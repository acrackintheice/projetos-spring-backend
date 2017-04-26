package hello.dao.old;

import hello.dao.HibernateAbstractDao;
import hello.model.Evento;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DU on 17/04/2017.
 */

@Repository("eventoDao")
public class EventoDaoImplHibernate extends HibernateAbstractDao<Integer, Evento> implements EventoDao{


    /**
     * @inheritDoc
     *
     * Implementação utilizando o Hibernate
     */
    @Override
    public List<Evento> findAll() {
        return createEntityCriteria().list();
    }
}
