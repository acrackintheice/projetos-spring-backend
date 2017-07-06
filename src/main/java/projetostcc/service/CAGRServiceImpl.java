package projetostcc.service;

import br.ufsc.cagr.services.CAGRService;
import br.ufsc.cagr.services.output.*;
import br.ufsc.services.cagr.model.*;
import br.ufsc.services.cagr.output.ResultadoTransporNotas;
import br.ufsc.services.exception.WebServiceException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CAGRServiceImpl implements CAGRService {
    private final CAGRService proxy;

    private final Cache<Integer, CurriculoCurso> cacheCurriculoCurso;
    private final Cache<Integer, List<DisciplinaCurriculo>> cacheDisciplinasCurriculoCurso;
    private final Cache<Integer, Integer> cacheSemestreAtualCurso;

    public CAGRServiceImpl(CAGRService proxy) {
        this.proxy = proxy;
        this.cacheCurriculoCurso = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).build();
        this.cacheDisciplinasCurriculoCurso = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).build();
        this.cacheSemestreAtualCurso = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).build();
    }

    @Override
    public CurriculoCurso getCurriculoCurso(final Integer idCurso) throws WebServiceException {
        try {
            return cacheCurriculoCurso.get(idCurso, () -> proxy.getCurriculoCurso(idCurso));
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<DisciplinaCurriculo> getDisciplinasCurriculoCurso(Integer idCurso) throws WebServiceException {
        try {
            return cacheDisciplinasCurriculoCurso.get(idCurso, () -> proxy.getDisciplinasCurriculoCurso(idCurso));
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Integer getSemestreAtualCurso(Integer idCurso) {
        try {
            return cacheSemestreAtualCurso.get(idCurso, () -> proxy.getSemestreAtualCurso(idCurso));
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Aviso> getAvisosComunidade() throws WebServiceException {
        return proxy.getAvisosComunidade();
    }

    @Override
    public List<Aviso> getAvisosAlunoByMatricula(Long aLong) throws WebServiceException {
        return proxy.getAvisosAlunoByMatricula(aLong);
    }

    @Override
    public List<Aviso> getAvisosProfessorBySerpro(Long aLong) throws WebServiceException {
        return proxy.getAvisosProfessorBySerpro(aLong);
    }

    @Override
    public List<Professor> getProfessoresByDepartamento(String s) {
        return proxy.getProfessoresByDepartamento(s);
    }

    @Override
    public List<CursoGraduacao> getCursosGraduacaoByCentro(String s) {
        return proxy.getCursosGraduacaoByCentro(s);
    }

    @Override
    public List<Departamento> getAllDepartamento() {
        return proxy.getAllDepartamento();
    }

    @Override
    public List<Departamento> getDepartamentosByCentro(String s) {
        return proxy.getDepartamentosByCentro(s);
    }

    @Override
    public List<CursoGraduacao> getAllCursoGraduacao() {
        return proxy.getAllCursoGraduacao();
    }

    @Override
    public GradeHorarioFaseCurso getGradeHorarioCursoByFase(Integer integer, Integer integer1) throws WebServiceException {
        return proxy.getGradeHorarioCursoByFase(integer, integer1);
    }

    @Override
    public GradeHorarioAluno getGradeHorarioAluno(Long aLong) throws WebServiceException {
        return proxy.getGradeHorarioAluno(aLong);
    }

    @Override
    public CurriculoCurso getCurriculoCursoByCurriculo(Integer integer, Integer integer1) throws WebServiceException {
        return proxy.getCurriculoCursoByCurriculo(integer, integer1);
    }

    @Override
    public ControleCurricularAluno getControleCurricularByAluno(Long aLong) throws WebServiceException {
        return proxy.getControleCurricularByAluno(aLong);
    }

    @Override
    public FotoCAGR getFotoAluno(Long aLong) throws WebServiceException {
        return proxy.getFotoAluno(aLong);
    }

    @Override
    public FotoCAGR getFotoProfessor(Long aLong) throws WebServiceException {
        return proxy.getFotoProfessor(aLong);
    }

    @Override
    public HistoricoEscolarAluno getHistoricoEscolarByAluno(Long aLong) throws WebServiceException {
        return proxy.getHistoricoEscolarByAluno(aLong);
    }

    @Override
    public Integer getSemestreAtual() {
        return proxy.getSemestreAtual();
    }

    @Override
    public GradeHorarioAluno getGradeHorarioAlunoSemestre(Long aLong, Integer integer) throws WebServiceException {
        return proxy.getGradeHorarioAlunoSemestre(aLong, integer);
    }

    @Override
    public List<Integer> getSemestresAluno(Long aLong) throws WebServiceException {
        return proxy.getSemestresAluno(aLong);
    }

    @Override
    public Integer getUltimoSemestreDisciplinaAluno(Long aLong) throws WebServiceException {
        return proxy.getUltimoSemestreDisciplinaAluno(aLong);
    }

    @Override
    public List<Integer> getSemestresCadastroTurma() {
        return proxy.getSemestresCadastroTurma();
    }

    @Override
    public CurriculoCurso getCurriculoCursoAluno(Long aLong) throws WebServiceException {
        return proxy.getCurriculoCursoAluno(aLong);
    }

    @Override
    public String getURLPesquisaPendenteAluno(Long aLong) throws WebServiceException {
        return proxy.getURLPesquisaPendenteAluno(aLong);
    }

    @Override
    public String getMensagemBloqueioCAGR() {
        return proxy.getMensagemBloqueioCAGR();
    }

    @Override
    public DataPeriodo getPeriodoProximaMatricula() {
        return proxy.getPeriodoProximaMatricula();
    }

    @Override
    public DataPeriodo getPeriodoAgendaGraduacao(String s) {
        return proxy.getPeriodoAgendaGraduacao(s);
    }

    @Override
    public EspelhoMatriculaAluno getEspelhoMatriculaAlunoSemestre(Long aLong, Integer integer) throws WebServiceException {
        return proxy.getEspelhoMatriculaAlunoSemestre(aLong, integer);
    }

    @Override
    public EspelhoMatriculaAluno getEspelhoMatriculaAluno(Long aLong) throws WebServiceException {
        return proxy.getEspelhoMatriculaAluno(aLong);
    }

    @Override
    public InformacaoAluno getInformacaoAluno(Long aLong) throws WebServiceException {
        return proxy.getInformacaoAluno(aLong);
    }

    @Override
    public List<IAAMedioCursoGraduacao> getIAAMedioCursos() {
        return proxy.getIAAMedioCursos();
    }

    @Override
    public List<IAAMedioCursoGraduacao> getIAAMedioCursoSemestres(Integer integer, Integer integer1, Integer integer2) throws WebServiceException {
        return proxy.getIAAMedioCursoSemestres(integer, integer1, integer2);
    }

    @Override
    public IAAMedioCursoGraduacao getIAAMedioCurso(Integer integer) {
        return proxy.getIAAMedioCurso(integer);
    }

    @Override
    public InformacaoAluno getInformacaoCompletaAluno(Long aLong) throws WebServiceException {
        return proxy.getInformacaoCompletaAluno(aLong);
    }

    @Override
    public InformacaoAluno getInformacaoCompletaAlunoByMatriculaSenha(Long aLong, String s) throws WebServiceException {
        return proxy.getInformacaoCompletaAlunoByMatriculaSenha(aLong, s);
    }

    @Override
    public CursoGraduacao getCursoGraduacaoAluno(Long aLong) throws WebServiceException {
        return proxy.getCursoGraduacaoAluno(aLong);
    }

    @Override
    public List<HistoricoEscolarDisciplina> getDisciplinasHistoricoEscolar(Long aLong, boolean b, boolean b1) throws WebServiceException {
        return proxy.getDisciplinasHistoricoEscolar(aLong, b, b1);
    }

    @Override
    public List<InformacaoAluno> getInformacaoCompletaAlunosByCPFNome(Long aLong, String s) {
        return proxy.getInformacaoCompletaAlunosByCPFNome(aLong, s);
    }

    @Override
    public List<InformacaoAluno> getInformacaoCompletaAlunosMatriculadosByCodigoDisciplina(String s) {
        return proxy.getInformacaoCompletaAlunosMatriculadosByCodigoDisciplina(s);
    }

    @Override
    public List<InformacaoAluno> getInformacaoCompletaAlunosMatriculadosByCodigoDisciplinaSemestre(String s, Integer integer) {
        return proxy.getInformacaoCompletaAlunosMatriculadosByCodigoDisciplinaSemestre(s, integer);
    }

    @Override
    public List<InformacaoAluno> getInformacaoCompletaAlunosComFotoByCPF(Long aLong) {
        return proxy.getInformacaoCompletaAlunosComFotoByCPF(aLong);
    }

    @Override
    public List<DisciplinaCurriculo> getDisciplinasCurriculoCursoByCurriculo(Integer integer, Integer integer1) throws WebServiceException {
        return proxy.getDisciplinasCurriculoCursoByCurriculo(integer, integer1);
    }

    @Override
    public List<DisciplinaCurriculo> getDisciplinasCurriculoCursoByCurriculoHabilitacao(Integer integer, Integer integer1, Integer integer2) throws WebServiceException {
        return proxy.getDisciplinasCurriculoCursoByCurriculoHabilitacao(integer, integer1, integer2);
    }

    @Override
    public DisciplinaCurriculo getDisciplinaCurriculoCurso(String s, Integer integer) throws WebServiceException {
        return proxy.getDisciplinaCurriculoCurso(s, integer);
    }

    @Override
    public DisciplinaCurriculo getDisciplinaCurriculoCursoByCurriculo(String s, Integer integer, Integer integer1) throws WebServiceException {
        return proxy.getDisciplinaCurriculoCursoByCurriculo(s, integer, integer1);
    }

    @Override
    public DisciplinaCurriculo getDisciplinaCurriculoCursoByCurriculoHabilitacao(String s, Integer integer, Integer integer1, Integer integer2) throws WebServiceException {
        return proxy.getDisciplinaCurriculoCursoByCurriculoHabilitacao(s, integer, integer1, integer2);
    }

    @Override
    public AtualizacaoNotaAluno atualizarNotaDisciplinaValidadaAluno(Long aLong, String s, Integer integer, BigDecimal bigDecimal, String s1, Long aLong1) throws WebServiceException {
        return proxy.atualizarNotaDisciplinaValidadaAluno(aLong, s, integer, bigDecimal, s1, aLong1);
    }

    @Override
    public List<CoordenacaoCurso> getSecretariasDepartamentoBySiapeSerpro(Long aLong) throws WebServiceException {
        return proxy.getSecretariasDepartamentoBySiapeSerpro(aLong);
    }

    @Override
    public List<CoordenacaoCurso> getSecretariasCursoBySiapeSerpro(Long aLong) throws WebServiceException {
        return proxy.getSecretariasCursoBySiapeSerpro(aLong);
    }

    @Override
    public List<CoordenacaoCurso> getCoordenadoriasCursoBySiapeSerpro(Long aLong) throws WebServiceException {
        return proxy.getCoordenadoriasCursoBySiapeSerpro(aLong);
    }

    @Override
    public List<CursoGraduacao> getCursosGraduacaoBySemestreCampusCadastroTurma(Integer integer, Integer integer1) throws WebServiceException {
        return proxy.getCursosGraduacaoBySemestreCampusCadastroTurma(integer, integer1);
    }

    @Override
    public Integer getSemestreAtualCadastroTurmas() {
        return proxy.getSemestreAtualCadastroTurmas();
    }

    @Override
    public Integer getNumeroUltimoCurriculoCurso(Integer integer) throws WebServiceException {
        return proxy.getNumeroUltimoCurriculoCurso(integer);
    }

    @Override
    public CurriculoCurso getInformacaoCurriculoCursoAluno(Long aLong) throws WebServiceException {
        return proxy.getInformacaoCurriculoCursoAluno(aLong);
    }

    @Override
    public List<InformacaoAluno> getInformacaoAlunosAtivosByCurso(Integer integer) throws WebServiceException {
        return proxy.getInformacaoAlunosAtivosByCurso(integer);
    }

    @Override
    public List<Professor> getProfessoresByCursoSemestreAtual(Integer integer) throws WebServiceException {
        return proxy.getProfessoresByCursoSemestreAtual(integer);
    }

    @Override
    public List<Professor> getProfessoresByCursoSemestre(Integer integer, Integer integer1) throws WebServiceException {
        return proxy.getProfessoresByCursoSemestre(integer, integer1);
    }

    @Override
    public List<Departamento> getDepartamentosChefiaBySiapeSerpro(Long aLong) {
        return proxy.getDepartamentosChefiaBySiapeSerpro(aLong);
    }

    @Override
    public boolean alterarSenhaAluno(Long aLong, String s, String s1) throws WebServiceException {
        return proxy.alterarSenhaAluno(aLong, s, s1);
    }

    @Override
    public boolean alterarEmailAluno(Long aLong, String s) throws WebServiceException {
        return proxy.alterarEmailAluno(aLong, s);
    }

    @Override
    public CoordenacaoCurso getCoordenadorCursoAluno(Long aLong) throws WebServiceException {
        return proxy.getCoordenadorCursoAluno(aLong);
    }

    @Override
    public CoordenacaoCurso getCoordenadorCurso(Integer integer) throws WebServiceException {
        return proxy.getCoordenadorCurso(integer);
    }

    @Override
    public CoordenacaoCurso getSecretarioDepartamento(String s) throws WebServiceException {
        return proxy.getSecretarioDepartamento(s);
    }

    @Override
    public String forceIndexarTurmasSemestreAtual() {
        return proxy.forceIndexarTurmasSemestreAtual();
    }

    @Override
    public String forceIndexarTodasTurmas() {
        return proxy.forceIndexarTodasTurmas();
    }

    @Override
    public Boolean alterarEstrangeiroEmAlunoComunidade(AlunoComunidade alunoComunidade) throws WebServiceException {
        return proxy.alterarEstrangeiroEmAlunoComunidade(alunoComunidade);
    }

    @Override
    public Integer adicionarEstrangeiroEmAlunoComunidade(AlunoComunidade alunoComunidade) throws WebServiceException {
        return proxy.adicionarEstrangeiroEmAlunoComunidade(alunoComunidade);
    }

    @Override
    public List<ResultadoMatricula> matricularAlunoEspecialEmDisciplina(Integer integer, Integer integer1, String s, String s1, Boolean aBoolean, List<TurmaGraduacaoPK> list) throws WebServiceException {
        return proxy.matricularAlunoEspecialEmDisciplina(integer, integer1, s, s1, aBoolean, list);
    }

    @Override
    public String forceIndexarTurmasSemestre(Integer integer) {
        return proxy.forceIndexarTurmasSemestre(integer);
    }

    @Override
    public Integer getNumeroTurmasGraduacaoIndexadas() {
        return proxy.getNumeroTurmasGraduacaoIndexadas();
    }

    @Override
    public Integer getNumeroTurmasGraduacaoIndexadasSemestre(Integer integer) {
        return proxy.getNumeroTurmasGraduacaoIndexadasSemestre(integer);
    }

    @Override
    public RetornoRecuperarSenhaAluno enviarEmailRecuperarSenhaAluno(Long aLong, Long aLong1, Date date) {
        return proxy.enviarEmailRecuperarSenhaAluno(aLong, aLong1, date);
    }

    @Override
    public AlocacaoHorariaEspacoFisico getAlocacaoHorariaEspacoFisico(String s, String s1, Integer integer) throws WebServiceException {
        return proxy.getAlocacaoHorariaEspacoFisico(s, s1, integer);
    }

    @Override
    public AgendaGraduacao getAgendaGraduacao(String s) {
        return proxy.getAgendaGraduacao(s);
    }

    @Override
    public InformacaoMatriculaAlunoEad getInformacaoPeriodoMatriculaAlunoEad(Long aLong) throws WebServiceException {
        return proxy.getInformacaoPeriodoMatriculaAlunoEad(aLong);
    }

    @Override
    public void matricularAlunoEadEmDisciplinas(List<DisciplinaMatricula> list, Long aLong) throws WebServiceException {
        proxy.matricularAlunoEadEmDisciplinas(list, aLong);
    }

    @Override
    public InformacaoDisciplina getInformacaoDisciplina(String s) {
        return proxy.getInformacaoDisciplina(s);
    }

    @Override
    public InformacaoCadastroEspecial getInformacaoCompletaCadastroEspecial(Integer integer) throws WebServiceException {
        return proxy.getInformacaoCompletaCadastroEspecial(integer);
    }

    @Override
    public InformacaoCadastroEspecial getInformacaoCompletaCadastroEspecialByCPF(Long aLong) throws WebServiceException {
        return proxy.getInformacaoCompletaCadastroEspecialByCPF(aLong);
    }

    @Override
    public InformacaoCadastroEspecial getInformacaoCompletaCadastroEspecialByMatriculaSenha(Integer integer, String s) throws WebServiceException {
        return proxy.getInformacaoCompletaCadastroEspecialByMatriculaSenha(integer, s);
    }

    @Override
    public boolean alterarEmailCadastroEspecial(Integer integer, String s) throws WebServiceException {
        return proxy.alterarEmailCadastroEspecial(integer, s);
    }

    @Override
    public boolean alterarSenhaCadastroEspecial(Integer integer, String s, String s1) throws WebServiceException {
        return proxy.alterarSenhaCadastroEspecial(integer, s, s1);
    }

    @Override
    public String enviarEmailRecuperarSenhaCadastroEspecial(Long aLong, String s, Date date) throws WebServiceException {
        return proxy.enviarEmailRecuperarSenhaCadastroEspecial(aLong, s, date);
    }

    @Override
    public boolean isProfessorMinistrandoSemestreAtualBySerpro(Long aLong) throws WebServiceException {
        return proxy.isProfessorMinistrandoSemestreAtualBySerpro(aLong);
    }

    @Override
    public List<CursoGraduacaoInfo> getCursosGraduacaoInfoByCentro(String s) {
        return proxy.getCursosGraduacaoInfoByCentro(s);
    }

    @Override
    public List<InformacaoDisciplinaOferecida> getDisciplinasOferecidasCursoBySemestreAtual(Integer integer) throws WebServiceException {
        return proxy.getDisciplinasOferecidasCursoBySemestreAtual(integer);
    }

    @Override
    public List<InformacaoDisciplinaOferecida> getDisciplinasOferecidasCursoBySemestre(Integer integer, Integer integer1) throws WebServiceException {
        return proxy.getDisciplinasOferecidasCursoBySemestre(integer, integer1);
    }

    @Override
    public List<InformacaoDisciplinaOferecida> getDisciplinasOferecidasDepartamentoBySemestreAtual(String s) throws WebServiceException {
        return proxy.getDisciplinasOferecidasDepartamentoBySemestreAtual(s);
    }

    @Override
    public List<InformacaoDisciplinaOferecida> getDisciplinasOferecidasDepartamentoBySemestre(String s, Integer integer) throws WebServiceException {
        return proxy.getDisciplinasOferecidasDepartamentoBySemestre(s, integer);
    }

    @Override
    public List<InformacaoDisciplinaOferecida> getDisciplinasOferecidasByCodigos(String... strings) throws WebServiceException {
        return proxy.getDisciplinasOferecidasByCodigos(strings);
    }

    @Override
    public List<InformacaoDisciplinaOferecida> getDisciplinaOferecidaBySemestreCodigo(Integer integer, String s) throws WebServiceException {
        return proxy.getDisciplinaOferecidaBySemestreCodigo(integer, s);
    }

    @Override
    public List<InformacaoDisciplinaOferecida> getDisciplinasOferecidasBySemestreCodigos(Integer integer, String... strings) throws WebServiceException {
        return proxy.getDisciplinasOferecidasBySemestreCodigos(integer, strings);
    }

    @Override
    public List<InformacaoDisciplinaOferecida> getDisciplinasCurriculoAlunoRestantesBySemestreMatricula(Integer integer, Long aLong) throws WebServiceException {
        return proxy.getDisciplinasCurriculoAlunoRestantesBySemestreMatricula(integer, aLong);
    }

    @Override
    public List<InformacaoDisciplinaOferecida> getDisciplinasCursoAlunoBySemestreMatricula(Integer integer, Long aLong) throws WebServiceException {
        return proxy.getDisciplinasCursoAlunoBySemestreMatricula(integer, aLong);
    }

    @Override
    public InformacaoDisciplinaOferecida getDisciplinaOferecidaByCodigoTurmaSemestre(String s, String s1, Integer integer) throws WebServiceException {
        return proxy.getDisciplinaOferecidaByCodigoTurmaSemestre(s, s1, integer);
    }

    @Override
    public List<InformacaoDisciplinaOferecida> findDisciplinasOferecidasByCodigoNomeSemestre(String s, Integer integer) throws WebServiceException {
        return proxy.findDisciplinasOferecidasByCodigoNomeSemestre(s, integer);
    }

    @Override
    public ResultadoTransporNotas enviarNotaEspelhoMatricula(String s, String s1, Integer integer, Long aLong, BigDecimal bigDecimal, String s2, Boolean aBoolean) throws WebServiceException {
        return proxy.enviarNotaEspelhoMatricula(s, s1, integer, aLong, bigDecimal, s2, aBoolean);
    }

    @Override
    public List<Map<String, String>> autocompleteProfessor(String s) {
        return proxy.autocompleteProfessor(s);
    }

    @Override
    public List<Map<String, String>> autocompleteDisciplina(String s) {
        return proxy.autocompleteDisciplina(s);
    }
}
