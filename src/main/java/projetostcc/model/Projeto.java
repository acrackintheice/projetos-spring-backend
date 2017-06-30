package projetostcc.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by DU on 13/04/2017.
 */
@Entity
@Table(name = "projetos")
public class Projeto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_projeto;

    private Integer id_curso;

    @JsonView(View.Projeto.class)
    @OneToMany(mappedBy = "projeto")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Usuario> autores;

    @JsonView(View.Simples.class)
    private boolean concluido;

    @JsonView(View.Simples.class)
    private String titulo;

    @JsonView(View.Simples.class)
    @Type(type="text")
    private String descricao;

    private Date data_criacao;

    @JsonView(View.Simples.class)
    @ManyToOne
    @JoinColumn(name = "id_pessoa_responsavel", referencedColumnName = "id_ufsc")
    private Usuario responsavel;

    @JsonView(View.Simples.class)
    @ManyToMany
    @JoinTable(name = "orientadores", joinColumns = {
            @JoinColumn(name = "id_projeto", insertable = false, updatable = false)},
                inverseJoinColumns = {
                    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_ufsc", nullable = false, updatable = false)})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Usuario> orientadores;

    @JsonView(View.Simples.class)
    @ManyToMany
    @JoinTable(name = "coorientadores", joinColumns = {
            @JoinColumn(name = "id_projeto", insertable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_ufsc", nullable = false, updatable = false)})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Usuario> coorientadores;

    @JsonView(View.Simples.class)
    @ManyToMany
    @JoinTable(name = "banca", joinColumns = {
            @JoinColumn(name = "id_projeto", insertable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_ufsc", nullable = false, updatable = false)})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Usuario> membrosDaBanca;

    @Column(name = "dt_excluido", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExcluido;

    public Set<Usuario> getAutores() {
        return autores;
    }

    public void setAutores(Set<Usuario> autores) {
        this.autores = autores;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId_projeto() {
        return id_projeto;
    }

    public void setId_projeto(Integer id_projeto) {
        this.id_projeto = id_projeto;
    }

    public Integer getId_curso() {
        return id_curso;
    }

    public void setId_curso(Integer id_curso) {
        this.id_curso = id_curso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public Set<Usuario> getOrientadores() {
        return orientadores;
    }

    public void setOrientadores(Set<Usuario> orientadores) {
        this.orientadores = orientadores;
    }

    public Set<Usuario> getCoorientadores() {
        return coorientadores;
    }

    public void setCoorientadores(Set<Usuario> coorientadores) {
        this.coorientadores = coorientadores;
    }

    public Set<Usuario> getMembrosDaBanca() {
        return membrosDaBanca;
    }

    public void setMembrosDaBanca(Set<Usuario> membrosDaBanca) {
        this.membrosDaBanca = membrosDaBanca;
    }

    public Date getDataExcluido() {
        return dataExcluido;
    }

    public void setDataExcluido(Date dataExcluido) {
        this.dataExcluido = dataExcluido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Projeto projeto = (Projeto) o;

        if (!id_projeto.equals(projeto.id_projeto)) return false;
        if (!autores.equals(projeto.autores)) return false;
        return titulo.equals(projeto.titulo);
    }

    @Override
    public int hashCode() {
        int result = id_projeto.hashCode();
        result = 31 * result + autores.hashCode();
        result = 31 * result + titulo.hashCode();
        return result;
    }
}
