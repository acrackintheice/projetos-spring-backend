package hello.dao.old;

import hello.model.Arquivo;

import java.util.List;

/**
 * Created by DU on 19/04/2017.
 */
public interface ArquivoDao {

    /**
     * Método que busca todos os arquivos do banco
     *
     * @return uma lista de arquivos
     */
    List<Arquivo> findAll();

}
