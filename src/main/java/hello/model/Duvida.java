package hello.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;


/**
 * Created by eduardo on 26/04/17.
 */

@Entity(name = "duvidas")
public class Duvida implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Type(type = "text")
    private String pergunta;

    @Type(type = "text")
    private String resposta;

    public Duvida() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Duvida duvida = (Duvida) o;

        if (id != duvida.id) return false;
        if (pergunta != null ? !pergunta.equals(duvida.pergunta) : duvida.pergunta != null) return false;
        return resposta != null ? resposta.equals(duvida.resposta) : duvida.resposta == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pergunta != null ? pergunta.hashCode() : 0);
        result = 31 * result + (resposta != null ? resposta.hashCode() : 0);
        return result;
    }
}
