package projetostcc.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(View.Simples.class)
    private Long id_ufsc;

    @JsonView(View.Completo.class)
    private Long matricula_ufsc;

    @JsonView(View.Simples.class)
    private String nome;

    @JsonView(View.Simples.class)
    private String email;

    @JsonView(View.Completo.class)
    private boolean habilitado;

    @JsonView(View.Completo.class)
    private Date data_criacao;

    @JsonView(View.Usuario.class)
    @ManyToOne
    @JoinColumn(name = "id_projeto")
    private Projeto projeto;

    @JsonView(View.Completo.class)
    @ManyToOne
    @JoinColumn(name = "disciplina_atual", referencedColumnName = "id_disciplina")
    private Disciplina disciplinaAtual;

    public Usuario(){};

    public Usuario(long id_ufsc, Long matricula_ufsc, String nome, String email) {
        this.habilitado = true;
        this.data_criacao = new Date();
        this.id_ufsc = id_ufsc;
        this.matricula_ufsc = matricula_ufsc;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUfsc() {
        return id_ufsc;
    }

    public void setIdUfsc(Long id_ufsc) {
        this.id_ufsc = id_ufsc;
    }

    public Long getMatriculaUfsc() {
        return matricula_ufsc;
    }

    public void setMatriculaUfsc(Long matricula_ufsc) {
        this.matricula_ufsc = matricula_ufsc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Date getDataCriacao() {
        return data_criacao;
    }

    public void setDataCriacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Disciplina getDisciplinaAtual() {
        return disciplinaAtual;
    }

    public void setDisciplinaAtual(Disciplina disciplinaAtual) {
        this.disciplinaAtual = disciplinaAtual;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return id_ufsc.equals(usuario.id_ufsc);
    }

    @Override
    public int hashCode() {
        int result = 31 * id_ufsc.hashCode();
        return result;
    }
}
