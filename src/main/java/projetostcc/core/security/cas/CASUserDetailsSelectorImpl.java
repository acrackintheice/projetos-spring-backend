package projetostcc.core.security.cas;

/*
import br.ufsc.cagr.services.CAGRService;
import br.ufsc.cagr.services.output.InformacaoAluno;
import br.ufsc.framework.services.security.CustomUser;
import br.ufsc.framework.services.security.cas.CASUserDetailsSelector;
import br.ufsc.projetostcc.dao.jdbc.UsuarioDAO;
import br.ufsc.projetostcc.model.entity.departamento.Curso;
import br.ufsc.projetostcc.model.entity.projeto.Usuario;
import br.ufsc.projetostcc.service.departamento.CursoService;
import br.ufsc.projetostcc.service.departamento.DisciplinaService;
import br.ufsc.projetostcc.service.informacao.PossivelResponsavelService;
import br.ufsc.projetostcc.service.projeto.UsuarioService;
import br.ufsc.projetostcc.service.util.UtilProjetoService;
import br.ufsc.projetostcc.util.ApplicationProperties;
import br.ufsc.services.exception.WebServiceException;
import br.ufsc.services.pessoa.interfaces.CadastroPessoaService;
import br.ufsc.services.pessoa.model.dto.PessoaDTO;
import br.ufsc.services.pessoa.model.dto.PessoaVinculoDTO;
import br.ufsc.services.pessoa.model.support.PessoaVinculoInfo;
import org.apache.commons.lang.math.NumberUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CASUserDetailsSelectorImpl implements CASUserDetailsSelector {

    private static final Logger LOGGER = LoggerFactory.getLogger(CASUserDetailsSelectorImpl.class);

    private final CadastroPessoaService cadastroPessoaService;

    private final DisciplinaService disciplinaService;

    private final UsuarioDAO usuarioDAO;

    private final CursoService cursoService;

    private final CAGRService cagrService;

    private final UtilProjetoService utilProjetoService;

    private final UsuarioService usuarioService;

    private final PossivelResponsavelService possivelResponsavelService;

    private final ApplicationProperties applicationProperties;

    @Autowired
    public CASUserDetailsSelectorImpl(CadastroPessoaService cadastroPessoaService,
                                      DisciplinaService disciplinaService,
                                      UsuarioDAO usuarioDAO,
                                      CursoService cursoService,
                                      CAGRService cagrService,
                                      UtilProjetoService utilProjetoService,
                                      UsuarioService usuarioService,
                                      PossivelResponsavelService possivelResponsavelService,
                                      ApplicationProperties applicationProperties) {
        this.cadastroPessoaService = cadastroPessoaService;
        this.disciplinaService = disciplinaService;
        this.usuarioDAO = usuarioDAO;
        this.cursoService = cursoService;
        this.cagrService = cagrService;
        this.utilProjetoService = utilProjetoService;
        this.usuarioService = usuarioService;
        this.possivelResponsavelService = possivelResponsavelService;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public UserDetails getUserDetailsByCASAttributePrincipal(AttributePrincipal principal) {

        String idPessoa = (String) principal.getAttributes().get("idPessoa");

        if (applicationProperties.isEnvDev()) {
//            idPessoa = "100000000311882"; // Cislaghi
//            idPessoa = "100000000394729"; // Jean Hauck
//            idPessoa = "100000000309570"; // Olinto
//            idPessoa = "100000000303654"; // Bruno Dekker
//            idPessoa = "100000000210016"; // Yuri Pereira
        }

        try {
            if (!NumberUtils.isDigits(idPessoa)) {
                return null;
            }

            PessoaDTO pessoaDTO = utilProjetoService.getPessoaById((new Long(idPessoa)));
            if (pessoaDTO == null) {
                return null;
            }

            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));

            List<Curso> cursosResponsaveis = Collections.emptyList();
            InformacaoAluno info = null;

            Long idUfsc = pessoaDTO.getId();

            List<PessoaVinculoDTO> pessoaVinculos = cadastroPessoaService.getVinculosPessoaById(idUfsc);
            for (PessoaVinculoDTO pessoaVinculo : pessoaVinculos) {
                if (!pessoaVinculo.isAtivo()) {
                    continue;
                }

                if (pessoaVinculo.getCodigoVinculo().equals(PessoaVinculoInfo.ALUNO_GRADUACAO)) {

                    if (verificaCursoAlunoHabilitado(pessoaVinculo.getCodigoCurso())) {
                        roles.add(new SimpleGrantedAuthority("ROLE_ALUNO"));
                    } else {
                        throw new LoginException("usuario nao acessa");
                    }

                    info = cagrService.getInformacaoAluno(new Long(pessoaVinculo.getId()));

                } else {
                    roles.add(new SimpleGrantedAuthority("ROLE_AVALIADOR"));

                    if (possivelResponsavelService.isPossivelResponsavel(idUfsc)) {
                        roles.add(new SimpleGrantedAuthority("ROLE_POSSIVEL_RESPONSAVEL"));
                    }

                    //verifica se ele eh responsavel por algum curso (admin curso)
                    cursosResponsaveis = verificaAdminCurso(idUfsc);
                    if (cursosResponsaveis != null && !cursosResponsaveis.isEmpty()) {
                        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN_CURSO"));
                    }
                }
            }

            //relaciona o usuario do cagr com o usuario de tcc
            //verifica se ele ja utilizou o site de projetos, se esta na base
            Usuario usuarioTCC = usuarioDAO.findOneByIdUfsc(idUfsc);

            //se nao usou, salva seu registro
            if (usuarioTCC == null) {
                usuarioTCC = salvarNovoUsuario(idUfsc, info, pessoaDTO);
            } else {
                usuarioTCC = updateInfoUsuario(idUfsc, info, pessoaDTO);

                if (usuarioTCC.isAdmin()) {
                    roles.add(new SimpleGrantedAuthority("ROLE_ADMIN_GERAL"));
                }
            }

            usuarioTCC.setCursosResponsaveis(cursosResponsaveis);
            usuarioTCC.setInformacaoAluno(info);

            if (LOGGER.isInfoEnabled()) {
                String nomeCurso = info != null ? info.getNomeCurso() : "";

                LOGGER.info(
                    "[LOGIN] idUfsc: {} - nome: {} - curso: {} - roles: {}",
                    usuarioTCC.getIdUfsc(), usuarioTCC.getNome(), nomeCurso, roles
                );
            }

            return new CustomUser(idPessoa, "", true, roles, idUfsc, pessoaDTO.getNome(), usuarioTCC);
        } catch (LoginException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

    private Usuario salvarNovoUsuario(Long idUfsc, InformacaoAluno informacaoAluno, PessoaDTO pessoaDTO) throws WebServiceException {
        Usuario usuario = new Usuario();
        usuario.setIdUfsc(idUfsc);

        preencheUsuario(usuario, pessoaDTO, informacaoAluno);

        usuarioService.salvar(usuario);
        return usuarioService.findOneByIdUfsc(idUfsc);
    }

    private Usuario updateInfoUsuario(Long idUfsc, InformacaoAluno informacaoAluno, PessoaDTO pessoaDTO) throws WebServiceException {
        Usuario usuario = usuarioService.findOneByIdUfsc(idUfsc);

        preencheUsuario(usuario, pessoaDTO, informacaoAluno);

        usuarioService.salvar(usuario);
        return usuarioService.findOneByIdUfsc(idUfsc);
    }

    private Usuario preencheUsuario(Usuario usuario, PessoaDTO pessoaDTO, InformacaoAluno informacaoAluno) throws WebServiceException {
        if (informacaoAluno != null) {
            usuario.setMatriculaUfsc(informacaoAluno.getMatricula());
            usuario.setInformacaoAluno(informacaoAluno);
            usuario.setDisciplinaAtual(disciplinaService.getDisciplinaAtual(usuario, informacaoAluno));
            if (applicationProperties.isEnvDev()) {
//                usuario.setDisciplinaAtual(new Disciplina("INE5638"));
            }
        }

        utilProjetoService.setNomeSocial(pessoaDTO);

        usuario.setNome(pessoaDTO.getNome());
        usuario.setEmail(pessoaDTO.getEmailPreferencial());
        return usuario;
    }


    private boolean verificaCursoAlunoHabilitado(Long idCurso) {
        Curso c = cursoService.find(idCurso);
        return c != null;
    }

    private List<Curso> verificaAdminCurso(Long idPessoa) {
        return cursoService.findAllByProfResponsavel(idPessoa);
    }

}
*/