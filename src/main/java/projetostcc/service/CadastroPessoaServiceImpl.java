package projetostcc.service;

import br.ufsc.services.exception.WebServiceException;
import br.ufsc.services.pessoa.interfaces.CadastroPessoaService;
import br.ufsc.services.pessoa.model.dto.*;
import br.ufsc.services.pessoa.model.support.InformacaoVinculacao;
import br.ufsc.services.pessoa.model.support.MapPessoa;
import br.ufsc.services.pessoa.model.support.MapPessoaVinculo;
import br.ufsc.services.pessoa.output.*;
import br.ufsc.services.util.JAXBStringMapAdapter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CadastroPessoaServiceImpl implements CadastroPessoaService {

    private final CadastroPessoaService proxy;

    private final Cache<Long, Optional<PessoaFotoDTO>> cacheFotoPreferencialByIdPessoa;

    public CadastroPessoaServiceImpl(CadastroPessoaService proxy) {
        this.proxy = proxy;
        this.cacheFotoPreferencialByIdPessoa = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).build();
    }

    @Override
    public PessoaFotoDTO getFotoPreferencialByIdPessoa(final Long idPessoa, final boolean b) throws WebServiceException {
        try {
            Optional<PessoaFotoDTO> foto = cacheFotoPreferencialByIdPessoa.get(idPessoa, () -> {
                PessoaFotoDTO foto1 = proxy.getFotoPreferencialByIdPessoa(idPessoa, b);
                return foto1 != null ? Optional.of(foto1) : Optional.empty();
            });
            return foto.orElse(null);
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean indexarByIdsVinculos(List<String> list) throws WebServiceException {
        return proxy.indexarByIdsVinculos(list);
    }

    @Override
    public boolean atualizarPessoasByIdsVinculos(List<String> list) throws WebServiceException {
        return proxy.atualizarPessoasByIdsVinculos(list);
    }

    @Override
    public ResultadoInsercao autoCadastroInserirPessoa(PessoaDTO pessoaDTO) throws WebServiceException {
        return proxy.autoCadastroInserirPessoa(pessoaDTO);
    }

    @Override
    public ResultadoAtualizacao autoCadastroAtualizarPessoa(PessoaDTO pessoaDTO) throws WebServiceException {
        return proxy.autoCadastroAtualizarPessoa(pessoaDTO);
    }

    @Override
    public ResultadoInsercao inserirPessoa(PessoaDTO pessoaDTO, Long aLong) throws WebServiceException {
        return proxy.inserirPessoa(pessoaDTO, aLong);
    }

    @Override
    public ResultadoInsercao inserirPessoaParams(PessoaDTO pessoaDTO, Long aLong, Map<String, String> map) throws WebServiceException {
        return proxy.inserirPessoaParams(pessoaDTO, aLong, map);
    }

    @Override
    public ResultadoAtualizacao atualizarPessoa(PessoaDTO pessoaDTO, Long aLong) throws WebServiceException {
        return proxy.atualizarPessoa(pessoaDTO, aLong);
    }

    @Override
    public ResultadoAtualizacao atualizarPessoaParams(PessoaDTO pessoaDTO, Long aLong, Map<String, String> map) throws WebServiceException {
        return proxy.atualizarPessoaParams(pessoaDTO, aLong, map);
    }

    @Override
    public List<PessoaDocumentoDTO> getPessoaDocumentosByIdPessoa(Long aLong) throws WebServiceException {
        return proxy.getPessoaDocumentosByIdPessoa(aLong);
    }

    @Override
    public PessoaDocumentoDTO getPessoaDocumentoById(Long aLong) throws WebServiceException {
        return proxy.getPessoaDocumentoById(aLong);
    }

    @Override
    public ResultadoDefinicaoDocumentos removerPessoaDocumento(Long aLong, Long aLong1) throws WebServiceException {
        return proxy.removerPessoaDocumento(aLong, aLong1);
    }

    @Override
    public ResultadoDefinicaoDocumentos definirPessoaDocumentos(List<PessoaDocumentoDTO> list, Long aLong, Long aLong1) throws WebServiceException {
        return proxy.definirPessoaDocumentos(list, aLong, aLong1);
    }

    @Override
    public ResultadoDefinicaoDocumentos inserirPessoaDocumentos(List<PessoaDocumentoDTO> list, Long aLong, Long aLong1) throws WebServiceException {
        return proxy.inserirPessoaDocumentos(list, aLong, aLong1);
    }

    @Override
    public ResultadoDefinicaoEmails autoCadastroDefinirEmails(List<PessoaEmailDTO> list, Long aLong) throws WebServiceException {
        return proxy.autoCadastroDefinirEmails(list, aLong);
    }

    @Override
    public ResultadoDefinicaoEmails inserirPessoaEmails(List<PessoaEmailDTO> list, Long aLong, Long aLong1) throws WebServiceException {
        return proxy.inserirPessoaEmails(list, aLong, aLong1);
    }

    @Override
    public ResultadoEnvioConfirmacaoEmail enviarConfirmacaoEmail(Long aLong) throws WebServiceException {
        return proxy.enviarConfirmacaoEmail(aLong);
    }

    @Override
    public ResultadoConfirmacaoEmail confirmarEmail(String s, Long aLong) throws WebServiceException {
        return proxy.confirmarEmail(s, aLong);
    }

    @Override
    public InformacaoRequisicaoConfirmacaoEmail getInformacaoRequisicaoConfirmacaoEmail(String s) throws WebServiceException {
        return proxy.getInformacaoRequisicaoConfirmacaoEmail(s);
    }

    @Override
    public ResultadoAlteracaoSenha alterarSenha(Long aLong, String s, String s1) throws WebServiceException {
        return proxy.alterarSenha(aLong, s, s1);
    }

    @Override
    public ResultadoAlteracaoSenha alterarSenhaPessoa(Long aLong, String s, Long aLong1, String s1) throws WebServiceException {
        return proxy.alterarSenhaPessoa(aLong, s, aLong1, s1);
    }

    @Override
    public ResultadoAlteracaoSenha alterarSenhaBySolicitacao(String s, String s1) throws WebServiceException {
        return proxy.alterarSenhaBySolicitacao(s, s1);
    }

    @Override
    public ResultadoVerificacaoPessoa verificarPessoa(Long aLong, Long aLong1, String s) throws WebServiceException {
        return proxy.verificarPessoa(aLong, aLong1, s);
    }

    @Override
    public ResultadoSolicitacaoNovaSenha solicitarNovaSenha(String s) throws WebServiceException {
        return proxy.solicitarNovaSenha(s);
    }

    @Override
    public ResultadoSolicitacaoNovaSenha solicitarNovaSenhaRedirecionamentoAplicacao(String s, String s1, String s2) throws WebServiceException {
        return proxy.solicitarNovaSenhaRedirecionamentoAplicacao(s, s1, s2);
    }

    @Override
    public ResultadoConfirmacaoSolicitacaoNovaSenha confirmarSolicitacaoNovaSenha(String s) throws WebServiceException {
        return proxy.confirmarSolicitacaoNovaSenha(s);
    }

    @Override
    public List<PessoaFotoDTO> getFotosVinculosPessoaById(Long aLong, boolean b) throws WebServiceException {
        return proxy.getFotosVinculosPessoaById(aLong, b);
    }

    @Override
    public List<PessoaQualificador> getQualificadoresByIdPessoaQuery(Long aLong, String... strings) throws WebServiceException {
        return proxy.getQualificadoresByIdPessoaQuery(aLong, strings);
    }

    @Override
    public List<PessoaQualificador> getQualificadoresPessoaByQuery(String... strings) throws WebServiceException {
        return proxy.getQualificadoresPessoaByQuery(strings);
    }

    @Override
    public List<PessoaQualificador> getQualificadoresByIdPessoa(Long aLong) throws WebServiceException {
        return proxy.getQualificadoresByIdPessoa(aLong);
    }

    @Override
    public List<PessoaVinculoDTO> getVinculosPessoaById(Long aLong) throws WebServiceException {
        return proxy.getVinculosPessoaById(aLong);
    }

    @Override
    public List<PessoaVinculoDTO> getVinculosPessoaByIds(List<Long> list) throws WebServiceException {
        return proxy.getVinculosPessoaByIds(list);
    }

    @Override
    public List<PessoaVinculoDTO> getVinculosPessoaByCPF(Long aLong) throws WebServiceException {
        return proxy.getVinculosPessoaByCPF(aLong);
    }

    @Override
    public List<PessoaVinculoDTO> getVinculosPessoaByPassaporte(String s) throws WebServiceException {
        return proxy.getVinculosPessoaByPassaporte(s);
    }

    @Override
    public InformacaoIdUFSC getInformacaoIdUFSCByLogin(String s) throws WebServiceException {
        return proxy.getInformacaoIdUFSCByLogin(s);
    }

    @Override
    public InformacaoIdUFSC getInformacaoIdUFSCByIdPessoa(Long aLong) throws WebServiceException {
        return proxy.getInformacaoIdUFSCByIdPessoa(aLong);
    }

    @Override
    public InformacaoIdUFSC getInformacaoIdUFSCByCPF(Long aLong) throws WebServiceException {
        return proxy.getInformacaoIdUFSCByCPF(aLong);
    }

    @Override
    public ResultadoBuscaPessoa buscarPessoas(FiltroBuscaPessoa filtroBuscaPessoa) throws WebServiceException {
        return proxy.buscarPessoas(filtroBuscaPessoa);
    }

    @Override
    public ResultadoBuscaVinculo buscarVinculos(FiltroBuscaPessoa filtroBuscaPessoa) throws WebServiceException {
        return proxy.buscarVinculos(filtroBuscaPessoa);
    }

    @Override
    public ResultadoBuscaPessoa buscarVinculosRetornandoPessoasConsolidadas(FiltroBuscaPessoa filtroBuscaPessoa) throws WebServiceException {
        return proxy.buscarVinculosRetornandoPessoasConsolidadas(filtroBuscaPessoa);
    }

    @Override
    public List<PessoaDTO> findPessoaByLuceneQuery(String s) throws WebServiceException {
        return proxy.findPessoaByLuceneQuery(s);
    }

    @Override
    @XmlJavaTypeAdapter(JAXBStringMapAdapter.class)
    public List<MapPessoa> findMapPessoaByLuceneQuery(String s, String... strings) throws WebServiceException {
        return proxy.findMapPessoaByLuceneQuery(s, strings);
    }

    @Override
    public Long getIdPessoaByCPF(Long aLong) throws WebServiceException {
        return proxy.getIdPessoaByCPF(aLong);
    }

    @Override
    public Long getIdPessoaByLogin(String s) throws WebServiceException {
        return proxy.getIdPessoaByLogin(s);
    }

    @Override
    public Long getIdPessoaByPassaporte(String s) throws WebServiceException {
        return proxy.getIdPessoaByPassaporte(s);
    }

    @Override
    public Long getCPFByIdPessoa(Long aLong) throws WebServiceException {
        return proxy.getCPFByIdPessoa(aLong);
    }

    @Override
    public String getLoginByIdPessoa(Long aLong) throws WebServiceException {
        return proxy.getLoginByIdPessoa(aLong);
    }

    @Override
    public PessoaDTO removerPessoa(Long aLong, Long aLong1) throws WebServiceException {
        return proxy.removerPessoa(aLong, aLong1);
    }

    @Override
    public PessoaDTO renomearIdPessoa(Long aLong, Long aLong1, Long aLong2) throws WebServiceException {
        return proxy.renomearIdPessoa(aLong, aLong1, aLong2);
    }

    @Override
    @XmlJavaTypeAdapter(JAXBStringMapAdapter.class)
    public List<MapPessoa> findMapPessoaHistoricoByLuceneQuery(String s, String... strings) throws WebServiceException {
        return proxy.findMapPessoaHistoricoByLuceneQuery(s, strings);
    }

    @Override
    public PessoaDTO getPessoaHistoricoByIdRevisao(Long aLong, Long aLong1) throws WebServiceException {
        return proxy.getPessoaHistoricoByIdRevisao(aLong, aLong1);
    }

    @Override
    public PessoaDTO getPessoaByCPF(Long aLong) throws WebServiceException {
        return proxy.getPessoaByCPF(aLong);
    }

    @Override
    public PessoaDTO getPessoaByCPFParams(Long aLong, Map<String, String> map) throws WebServiceException {
        return proxy.getPessoaByCPFParams(aLong, map);
    }

    @Override
    public PessoaDTO getPessoaByPassaporte(String s) throws WebServiceException {
        return proxy.getPessoaByPassaporte(s);
    }

    @Override
    public PessoaDTO getPessoaByPassaporteParams(String s, Map<String, String> map) throws WebServiceException {
        return proxy.getPessoaByPassaporteParams(s, map);
    }

    @Override
    public PessoaDTO getPessoaByLogin(String s) throws WebServiceException {
        return proxy.getPessoaByLogin(s);
    }

    @Override
    public PessoaDTO getPessoaByLoginParams(String s, Map<String, String> map) throws WebServiceException {
        return proxy.getPessoaByLoginParams(s, map);
    }

    @Override
    public PessoaDTO getPessoaFromCacheById(Long aLong) throws WebServiceException {
        return proxy.getPessoaFromCacheById(aLong);
    }

    @Override
    public PessoaDTO getPessoaById(Long aLong) throws WebServiceException {
        return proxy.getPessoaById(aLong);
    }

    @Override
    public PessoaDTO getPessoaByIdParams(Long aLong, Map<String, String> map) throws WebServiceException {
        return proxy.getPessoaByIdParams(aLong, map);
    }

    @Override
    public PessoaDTO getPessoaByInformacaoVinculacao(InformacaoVinculacao informacaoVinculacao) throws WebServiceException {
        return proxy.getPessoaByInformacaoVinculacao(informacaoVinculacao);
    }

    @Override
    public List<PessoaDTO> getPessoasByIds(List<Long> list) throws WebServiceException {
        return proxy.getPessoasByIds(list);
    }

    @Override
    public Map<String, String> getMapPessoaByCPF(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByCPF(aLong, strings);
    }

    @Override
    public Map<String, String> getMapPessoaByPassaporte(String s, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByPassaporte(s, strings);
    }

    @Override
    public Map<String, String> getMapPessoaById(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapPessoaById(aLong, strings);
    }

    @Override
    public Map<String, String> getMapPessoaByInformacaoVinculacao(InformacaoVinculacao informacaoVinculacao, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByInformacaoVinculacao(informacaoVinculacao, strings);
    }

    @Override
    @XmlJavaTypeAdapter(JAXBStringMapAdapter.class)
    public List<MapPessoa> getMapPessoasByIds(List<Long> list, String... strings) throws WebServiceException {
        return proxy.getMapPessoasByIds(list, strings);
    }

    @Override
    public List<Long> getIdPessoasAlteradasSinceDate(Date date) throws WebServiceException {
        return proxy.getIdPessoasAlteradasSinceDate(date);
    }

    @Override
    public Boolean isEmailDisponivel(String s) throws WebServiceException {
        return proxy.isEmailDisponivel(s);
    }

    @Override
    public Boolean isEmailDisponivelByIdPessoa(String s, Long aLong) throws WebServiceException {
        return proxy.isEmailDisponivelByIdPessoa(s, aLong);
    }

    @Override
    public Boolean isPessoaExisteById(Long aLong) throws WebServiceException {
        return proxy.isPessoaExisteById(aLong);
    }

    @Override
    public Boolean isPessoaExisteByCPF(Long aLong) throws WebServiceException {
        return proxy.isPessoaExisteByCPF(aLong);
    }

    @Override
    public Boolean isPessoaExisteByPassaporte(String s) throws WebServiceException {
        return proxy.isPessoaExisteByPassaporte(s);
    }

    @Override
    public List<PessoaEmailDTO> getEmailsByIdPessoa(Long aLong) throws WebServiceException {
        return proxy.getEmailsByIdPessoa(aLong);
    }

    @Override
    public List<PessoaEmailDTO> getEmailsByIdPessoaEnviandoRequisicoesConfirmacao(Long aLong) throws WebServiceException {
        return proxy.getEmailsByIdPessoaEnviandoRequisicoesConfirmacao(aLong);
    }

    @Override
    public ResultadoBuscaEmailsByInformacaoVinculacao getEmailsPessoaByInformacaoVinculacao(InformacaoVinculacao informacaoVinculacao, Boolean aBoolean) throws WebServiceException {
        return proxy.getEmailsPessoaByInformacaoVinculacao(informacaoVinculacao, aBoolean);
    }

    @Override
    public PessoaVinculoDTO getInformacaoVinculoAlunoGraduacao(Integer integer) throws WebServiceException {
        return proxy.getInformacaoVinculoAlunoGraduacao(integer);
    }

    @Override
    public PessoaVinculoDTO getInformacaoVinculoAlunoPosGraduacao(Long aLong) throws WebServiceException {
        return proxy.getInformacaoVinculoAlunoPosGraduacao(aLong);
    }

    @Override
    public PessoaVinculoDTO getInformacaoVinculoCadastroEspecial(Integer integer) throws WebServiceException {
        return proxy.getInformacaoVinculoCadastroEspecial(integer);
    }

    @Override
    public PessoaVinculoDTO getInformacaoVinculoProfessorExternoPosGraduacao(Integer integer) throws WebServiceException {
        return proxy.getInformacaoVinculoProfessorExternoPosGraduacao(integer);
    }

    @Override
    public PessoaVinculoDTO getInformacaoVinculoFuncionario(Integer integer) throws WebServiceException {
        return proxy.getInformacaoVinculoFuncionario(integer);
    }

    @Override
    public PessoaVinculoDTO getInformacaoVinculoFuncionarioBySiape(Long aLong) throws WebServiceException {
        return proxy.getInformacaoVinculoFuncionarioBySiape(aLong);
    }

    @Override
    public PessoaVinculoDTO getInformacaoVinculoAlunoColegioAplicacao(Integer integer) throws WebServiceException {
        return proxy.getInformacaoVinculoAlunoColegioAplicacao(integer);
    }

    @Override
    public PessoaVinculoDTO getInformacaoVinculoMinistranteSGCA(Long aLong) throws WebServiceException {
        return proxy.getInformacaoVinculoMinistranteSGCA(aLong);
    }

    @Override
    public PessoaVinculoDTO getInformacaoVinculoParticipanteSGCA(Integer integer) throws WebServiceException {
        return proxy.getInformacaoVinculoParticipanteSGCA(integer);
    }

    @Override
    public PessoaVinculoDTO getInformacaoVinculoInscricaoPosGraduacao(Long aLong) throws WebServiceException {
        return proxy.getInformacaoVinculoInscricaoPosGraduacao(aLong);
    }

    @Override
    public PessoaVinculoDTO getInformacaoVinculoByIdVinculo(Integer integer, String s) throws WebServiceException {
        return proxy.getInformacaoVinculoByIdVinculo(integer, s);
    }

    @Override
    @XmlJavaTypeAdapter(JAXBStringMapAdapter.class)
    public List<MapPessoaVinculo> getMapVinculosPessoaById(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapVinculosPessoaById(aLong, strings);
    }

    @Override
    @XmlJavaTypeAdapter(JAXBStringMapAdapter.class)
    public List<MapPessoaVinculo> getMapVinculosPessoaByIds(List<Long> list, String... strings) throws WebServiceException {
        return proxy.getMapVinculosPessoaByIds(list, strings);
    }

    @Override
    @XmlJavaTypeAdapter(JAXBStringMapAdapter.class)
    public List<MapPessoaVinculo> getMapVinculosPessoaByCPF(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapVinculosPessoaByCPF(aLong, strings);
    }

    @Override
    @XmlJavaTypeAdapter(JAXBStringMapAdapter.class)
    public List<MapPessoaVinculo> getMapVinculosPessoaByPassaporte(String s, String... strings) throws WebServiceException {
        return proxy.getMapVinculosPessoaByPassaporte(s, strings);
    }

    @Override
    public Map<String, String> getMapInformacaoVinculoAlunoGraduacao(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapInformacaoVinculoAlunoGraduacao(integer, strings);
    }

    @Override
    public Map<String, String> getMapInformacaoVinculoAlunoPosGraduacao(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapInformacaoVinculoAlunoPosGraduacao(aLong, strings);
    }

    @Override
    public Map<String, String> getMapInformacaoVinculoCadastroEspecial(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapInformacaoVinculoCadastroEspecial(integer, strings);
    }

    @Override
    public Map<String, String> getMapInformacaoVinculoProfessorExternoPosGraduacao(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapInformacaoVinculoProfessorExternoPosGraduacao(integer, strings);
    }

    @Override
    public Map<String, String> getMapInformacaoVinculoFuncionario(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapInformacaoVinculoFuncionario(integer, strings);
    }

    @Override
    public Map<String, String> getMapInformacaoVinculoFuncionarioBySiape(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapInformacaoVinculoFuncionarioBySiape(aLong, strings);
    }

    @Override
    public Map<String, String> getMapInformacaoVinculoAlunoColegioAplicacao(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapInformacaoVinculoAlunoColegioAplicacao(integer, strings);
    }

    @Override
    public Map<String, String> getMapInformacaoVinculoMinistranteSGCA(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapInformacaoVinculoMinistranteSGCA(aLong, strings);
    }

    @Override
    public Map<String, String> getMapInformacaoVinculoParticipanteSGCA(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapInformacaoVinculoParticipanteSGCA(integer, strings);
    }

    @Override
    public Map<String, String> getMapInformacaoVinculoInscricaoPosGraduacao(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapInformacaoVinculoInscricaoPosGraduacao(aLong, strings);
    }

    @Override
    public Map<String, String> getMapInformacaoVinculoByIdVinculo(Integer integer, String s, String... strings) throws WebServiceException {
        return proxy.getMapInformacaoVinculoByIdVinculo(integer, s, strings);
    }

    @Override
    public List<String> autocompleteNome(String s) throws WebServiceException {
        return proxy.autocompleteNome(s);
    }

    @Override
    public Long getIdPessoaByMatriculaGraduacao(Integer integer) throws WebServiceException {
        return proxy.getIdPessoaByMatriculaGraduacao(integer);
    }

    @Override
    public Long getIdPessoaByMatriculaPosGraduacao(Long aLong) throws WebServiceException {
        return proxy.getIdPessoaByMatriculaPosGraduacao(aLong);
    }

    @Override
    public Long getIdPessoaByMatriculaCadastroEspecial(Integer integer) throws WebServiceException {
        return proxy.getIdPessoaByMatriculaCadastroEspecial(integer);
    }

    @Override
    public Long getIdPessoaByMatriculaProfessorExternoPosGraduacao(Integer integer) throws WebServiceException {
        return proxy.getIdPessoaByMatriculaProfessorExternoPosGraduacao(integer);
    }

    @Override
    public Long getIdPessoaByMatriculaFuncionario(Integer integer) throws WebServiceException {
        return proxy.getIdPessoaByMatriculaFuncionario(integer);
    }

    @Override
    public Long getIdPessoaBySiapeFuncionario(Long aLong) throws WebServiceException {
        return proxy.getIdPessoaBySiapeFuncionario(aLong);
    }

    @Override
    public Long getIdPessoaByMatriculaColegioAplicacao(Integer integer) throws WebServiceException {
        return proxy.getIdPessoaByMatriculaColegioAplicacao(integer);
    }

    @Override
    public Long getIdPessoaByCPFMinistranteSGCA(Long aLong) throws WebServiceException {
        return proxy.getIdPessoaByCPFMinistranteSGCA(aLong);
    }

    @Override
    public Long getIdPessoaByMatriculaParticipanteSGCA(Integer integer) throws WebServiceException {
        return proxy.getIdPessoaByMatriculaParticipanteSGCA(integer);
    }

    @Override
    public Long getIdPessoaByMatriculaInscricaoPosGraduacao(Long aLong) throws WebServiceException {
        return proxy.getIdPessoaByMatriculaInscricaoPosGraduacao(aLong);
    }

    @Override
    public Long getIdPessoaByIdVinculo(Integer integer, String s) throws WebServiceException {
        return proxy.getIdPessoaByIdVinculo(integer, s);
    }

    @Override
    public PessoaDTO getPessoaByMatriculaGraduacao(Integer integer) throws WebServiceException {
        return proxy.getPessoaByMatriculaGraduacao(integer);
    }

    @Override
    public PessoaDTO getPessoaByMatriculaPosGraduacao(Long aLong) throws WebServiceException {
        return proxy.getPessoaByMatriculaPosGraduacao(aLong);
    }

    @Override
    public PessoaDTO getPessoaByMatriculaCadastroEspecial(Integer integer) throws WebServiceException {
        return proxy.getPessoaByMatriculaCadastroEspecial(integer);
    }

    @Override
    public PessoaDTO getPessoaByMatriculaProfessorExternoPosGraduacao(Integer integer) throws WebServiceException {
        return proxy.getPessoaByMatriculaProfessorExternoPosGraduacao(integer);
    }

    @Override
    public PessoaDTO getPessoaByMatriculaFuncionario(Integer integer) throws WebServiceException {
        return proxy.getPessoaByMatriculaFuncionario(integer);
    }

    @Override
    public PessoaDTO getPessoaBySiapeFuncionario(Long aLong) throws WebServiceException {
        return proxy.getPessoaBySiapeFuncionario(aLong);
    }

    @Override
    public PessoaDTO getPessoaByMatriculaColegioAplicacao(Integer integer) throws WebServiceException {
        return proxy.getPessoaByMatriculaColegioAplicacao(integer);
    }

    @Override
    public PessoaDTO getPessoaByCPFMinistranteSGCA(Long aLong) throws WebServiceException {
        return proxy.getPessoaByCPFMinistranteSGCA(aLong);
    }

    @Override
    public PessoaDTO getPessoaByMatriculaParticipanteSGCA(Integer integer) throws WebServiceException {
        return proxy.getPessoaByMatriculaParticipanteSGCA(integer);
    }

    @Override
    public PessoaDTO getPessoaByMatriculaInscricaoPosGraduacao(Long aLong) throws WebServiceException {
        return proxy.getPessoaByMatriculaInscricaoPosGraduacao(aLong);
    }

    @Override
    public PessoaDTO getPessoaByIdVinculo(Integer integer, String s) throws WebServiceException {
        return proxy.getPessoaByIdVinculo(integer, s);
    }

    @Override
    public Map<String, String> getMapPessoaByMatriculaGraduacao(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByMatriculaGraduacao(integer, strings);
    }

    @Override
    public Map<String, String> getMapPessoaByMatriculaPosGraduacao(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByMatriculaPosGraduacao(aLong, strings);
    }

    @Override
    public Map<String, String> getMapPessoaByMatriculaCadastroEspecial(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByMatriculaCadastroEspecial(integer, strings);
    }

    @Override
    public Map<String, String> getMapPessoaByMatriculaProfessorExternoPosGraduacao(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByMatriculaProfessorExternoPosGraduacao(integer, strings);
    }

    @Override
    public Map<String, String> getMapPessoaByMatriculaFuncionario(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByMatriculaFuncionario(integer, strings);
    }

    @Override
    public Map<String, String> getMapPessoaBySiapeFuncionario(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapPessoaBySiapeFuncionario(aLong, strings);
    }

    @Override
    public Map<String, String> getMapPessoaByMatriculaColegioAplicacao(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByMatriculaColegioAplicacao(integer, strings);
    }

    @Override
    public Map<String, String> getMapPessoaByCPFMinistranteSGCA(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByCPFMinistranteSGCA(aLong, strings);
    }

    @Override
    public Map<String, String> getMapPessoaByMatriculaParticipanteSGCA(Integer integer, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByMatriculaParticipanteSGCA(integer, strings);
    }

    @Override
    public Map<String, String> getMapPessoaByMatriculaInscricaoPosGraduacao(Long aLong, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByMatriculaInscricaoPosGraduacao(aLong, strings);
    }

    @Override
    public Map<String, String> getMapPessoaByIdVinculo(Integer integer, String s, String... strings) throws WebServiceException {
        return proxy.getMapPessoaByIdVinculo(integer, s, strings);
    }

    @Override
    public List<ReferenciaPessoa> getReferenciasPessoaById(Long aLong) throws WebServiceException {
        return proxy.getReferenciasPessoaById(aLong);
    }

    @Override
    public List<String> getSCCPRoleNamesByIdPessoa(Long aLong) throws WebServiceException {
        return proxy.getSCCPRoleNamesByIdPessoa(aLong);
    }

    @Override
    public PermissaoCadastroPessoa getSCCPPermissaoCadastroPessoa(Long aLong, Long aLong1) throws WebServiceException {
        return proxy.getSCCPPermissaoCadastroPessoa(aLong, aLong1);
    }

    @Override
    public PermissaoCadastroPessoa getSCCPPermissaoCadastroPessoaByCasService(Long aLong) throws WebServiceException {
        return proxy.getSCCPPermissaoCadastroPessoaByCasService(aLong);
    }

    @Override
    public boolean isPossuiPendenciasAutenticacaoPessoa(Long aLong, String s) throws WebServiceException {
        return proxy.isPossuiPendenciasAutenticacaoPessoa(aLong, s);
    }

    @Override
    public boolean isPossuiTipoVinculoAtivo(Long aLong, Integer integer) throws WebServiceException {
        return proxy.isPossuiTipoVinculoAtivo(aLong, integer);
    }

    @Override
    public boolean isAlunoGraduacaoAtivo(Long aLong) throws WebServiceException {
        return proxy.isAlunoGraduacaoAtivo(aLong);
    }

    @Override
    public boolean isAlunoPosGraduacaoAtivo(Long aLong) throws WebServiceException {
        return proxy.isAlunoPosGraduacaoAtivo(aLong);
    }

    @Override
    public boolean isFuncionarioAtivo(Long aLong) throws WebServiceException {
        return proxy.isFuncionarioAtivo(aLong);
    }

    @Override
    public boolean isProfessorAtivo(Long aLong) throws WebServiceException {
        return proxy.isProfessorAtivo(aLong);
    }

    @Override
    public ResultadoPendenciasAutenticacaoPessoa getPendenciasAutenticacaoPessoa(Long aLong, String s) throws WebServiceException {
        return proxy.getPendenciasAutenticacaoPessoa(aLong, s);
    }

    @Override
    public boolean processamentoUsuarioCientePendencias(Long aLong, List<PendenciaAutenticacaoPessoa> list) throws WebServiceException {
        return proxy.processamentoUsuarioCientePendencias(aLong, list);
    }

    @Override
    public List<PessoaVinculoAutenticacao> getInformacaoVinculosDisponiveisAutenticacaoByIdPessoa(Long aLong, Integer... integers) throws WebServiceException {
        return proxy.getInformacaoVinculosDisponiveisAutenticacaoByIdPessoa(aLong, integers);
    }

    @Override
    public ResultadoAutenticacaoPessoa autenticarPessoaById(Long aLong, String s) throws WebServiceException {
        return proxy.autenticarPessoaById(aLong, s);
    }

    @Override
    public ResultadoAutenticacaoPessoa autenticarPessoaByIdentificacao(String s, String s1) throws WebServiceException {
        return proxy.autenticarPessoaByIdentificacao(s, s1);
    }

    @Override
    public ResultadoAutenticacaoPessoa autenticarPessoaByLogin(String s, String s1) throws WebServiceException {
        return proxy.autenticarPessoaByLogin(s, s1);
    }

    @Override
    public ResultadoAutenticacaoPessoa getInformacaoAutenticacaoByIdentificacao(String s) throws WebServiceException {
        return proxy.getInformacaoAutenticacaoByIdentificacao(s);
    }

    @Override
    public ResultadoAutenticacaoPessoa getInformacaoAutenticacaoByLogin(String s) throws WebServiceException {
        return proxy.getInformacaoAutenticacaoByLogin(s);
    }

    @Override
    public boolean notificarAlteracaoPessoaById(Long aLong) throws WebServiceException {
        return proxy.notificarAlteracaoPessoaById(aLong);
    }

    @Override
    public boolean enviarMensagemFilaJms(String s, String s1) throws WebServiceException {
        return proxy.enviarMensagemFilaJms(s, s1);
    }

    @Override
    public boolean enviarMensagensFilaJms(String s, List<String> list) throws WebServiceException {
        return proxy.enviarMensagensFilaJms(s, list);
    }
}
