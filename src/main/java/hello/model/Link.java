package hello.model;

import javax.persistence.*;

/**
 * Created by DU on 22/04/2017.
 */

@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_link;

    private  String url;

    private String descricao;

    public int getId_link() {
        return id_link;
    }

    public void setId_link(int id_link) {
        this.id_link = id_link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

        Link link = (Link) o;

        if (id_link != link.id_link) return false;
        if (url != null ? !url.equals(link.url) : link.url != null) return false;
        return descricao != null ? descricao.equals(link.descricao) : link.descricao == null;
    }

    @Override
    public int hashCode() {
        int result = id_link;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        return result;
    }
}
