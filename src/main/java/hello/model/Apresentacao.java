package hello.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "data_apresentacao")
public class Apresentacao {

    @JsonView(View.Simples.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonView(View.Simples.class)
    @Column(name = "data_inicio")
    Date dataInicio;

    @JsonView(View.Simples.class)
    @Column(name = "data_termino")
    Date dataTermino;

    @JsonView(View.Simples.class)
    @OneToOne
    @JoinColumn(name = "id_projeto")
    Projeto projeto;

    public Apresentacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Apresentacao that = (Apresentacao) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dataInicio != null ? !dataInicio.equals(that.dataInicio) : that.dataInicio != null) return false;
        if (dataTermino != null ? !dataTermino.equals(that.dataTermino) : that.dataTermino != null) return false;
        return projeto != null ? projeto.equals(that.projeto) : that.projeto == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dataInicio != null ? dataInicio.hashCode() : 0);
        result = 31 * result + (dataTermino != null ? dataTermino.hashCode() : 0);
        result = 31 * result + (projeto != null ? projeto.hashCode() : 0);
        return result;
    }
}
