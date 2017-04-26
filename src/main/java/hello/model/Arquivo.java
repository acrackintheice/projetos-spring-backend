package hello.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "arquivos")
public class Arquivo implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "nome_arquivo")
    String nome_arquivo;

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

    public String getNome_arquivo() {
        return nome_arquivo;
    }

    public void setNome_arquivo(String nome_arquivo) {
        this.nome_arquivo = nome_arquivo;
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
        if (nome_arquivo != null ? !nome_arquivo.equals(arquivo.nome_arquivo) : arquivo.nome_arquivo != null) return false;
        return descricao != null ? descricao.equals(arquivo.descricao) : arquivo.descricao == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nome_arquivo != null ? nome_arquivo.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        return result;
    }
}
