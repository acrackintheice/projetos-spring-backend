package hello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by DU on 13/04/2017.
 */
@Entity
@Table(name = "disciplinas")
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id_disciplina;

    private String id_disciplina_equivalente;

    private String nome;

    @Column(name = "apelido", nullable = true)
    private String apelido;

    private int id_curso;

    private int ordem;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(String id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public String getId_disciplina_equivalente() {
        return id_disciplina_equivalente;
    }

    public void setId_disciplina_equivalente(String id_disciplina_equivalente) {
        this.id_disciplina_equivalente = id_disciplina_equivalente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Disciplina that = (Disciplina) o;
        return Objects.equals(id_disciplina, that.id_disciplina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_disciplina);
    }
}
