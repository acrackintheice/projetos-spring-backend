package hello.dao;

import hello.model.Link;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DU on 22/04/2017.
 */
@Repository("linkDao")
public class LinkDaoImplHibernate extends HibernateAbstractDao<Integer, Link> implements LinkDao{

    @Override
    public List<Link> findAll() { return createEntityCriteria().list(); }
}
