package hello.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by DU on 18/04/2017.
 */

@Entity
@Table(name = "arquivos")
public class Arquivo implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String nomeArquivo;

    @Type(type="text")
    String descricao;

    String formato;

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arquivo arquivo = (Arquivo) o;

        if (id != arquivo.id) return false;
        if (nomeArquivo != null ? !nomeArquivo.equals(arquivo.nomeArquivo) : arquivo.nomeArquivo != null) return false;
        return descricao != null ? descricao.equals(arquivo.descricao) : arquivo.descricao == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nomeArquivo != null ? nomeArquivo.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        return result;
    }
}
