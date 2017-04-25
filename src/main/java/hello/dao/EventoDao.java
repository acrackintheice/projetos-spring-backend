package hello.dao;

import hello.model.Evento;

import java.util.List;

/**
 * Created by DU on 17/04/2017.
 */
public interface EventoDao {

    /**
     * @inheritDoc
     *
     * retuns
     */
    List<Evento> findAll();
}
