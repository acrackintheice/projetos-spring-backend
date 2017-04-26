package hello.dao.old;

import hello.dao.HibernateAbstractDao;
import hello.model.Arquivo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DU on 19/04/2017.
 */

@Repository("arquivoDao")
public class ArquivoDaoImplHibernate extends HibernateAbstractDao<Integer, Arquivo> implements ArquivoDao{

    /**
     * @inheritDoc
     *
     * Implementação utilizando o Hibernate
     */
    @Override
    public List<Arquivo> findAll() {
        return createEntityCriteria().list();
    }
}
