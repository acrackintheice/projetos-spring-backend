package hello.dao;

import hello.model.Evento;

import java.util.List;

/**
 * Created by DU on 17/04/2017.
 */
public interface EventoDao {

    List<Evento> findAll(boolean sorted, String sortField, String sortOrder, List<String> filterFields, List<String> filterValues, int from, int to);

    List<Evento> findAll();
}
