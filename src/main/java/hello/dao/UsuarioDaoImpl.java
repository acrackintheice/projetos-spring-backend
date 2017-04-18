package hello.dao;

import hello.model.Usuario;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("usuarioDao")
@Transactional
public class UsuarioDaoImpl extends AbstractDao<Long, Usuario> implements UsuarioDao {

    @Override
    public Usuario findByIdUfsc(Long idUfsc) {
        return null;
    }

    @Override
    public Usuario findByMatricula(Long matricula) {
        return null;
    }

    @Override
    public List<Usuario> findAllByProjeto(Integer idProjeto) {
        return null;
    }

    @Override
    public List<Usuario> findAll(boolean sorted, String sortField, String sortOrder, List<String> filterFields, List<String> filterValues, int from, int to) {
        Order order = (sortOrder.equals("asc")) ? Order.asc(sortField) : Order.desc(sortField);
        int max = Math.max(0, to-from);
        Criteria query = createEntityCriteria();
        if (filterFields.size() == filterValues.size())
            filterFields.stream().forEach( value -> query.add(Restrictions.like(value, "%" + filterValues.get(filterFields.indexOf(value)) + "%")));
        if(sorted)
            query.addOrder(order);
        return query.setFirstResult(from).setMaxResults(max).list();
    }

    @Override
    public Long total(List<String> filterFields, List<String> filterValues) {
        Criteria query = createEntityCriteria();
        if (filterFields.size() == filterValues.size())
            filterFields.stream().forEach( value -> query.add(Restrictions.like(value, "%" + filterValues.get(filterFields.indexOf(value)) + "%")));
        return (Long) query.setProjection(Projections.countDistinct("id")).uniqueResult();
    }
}

