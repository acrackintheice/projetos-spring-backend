package hello.dao;

import hello.model.Evento;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DU on 17/04/2017.
 */

@Repository("eventoDao")
public class EventoDaoImpl extends AbstractDao<Integer, Evento> implements EventoDao{

    @Override
    public List<Evento> findAll(boolean sorted, String sortField, String sortOrder, List<String> filterFields, List<String> filterValues, int from, int to) {
        return null;
    }

    @Override
    public List<Evento> findAll() {
        return createEntityCriteria().list();
    }
}
