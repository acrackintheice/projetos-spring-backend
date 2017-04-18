package hello.dao;

import hello.model.Projeto;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository("projetoDao")
public class ProjectDaoImpl extends AbstractDao<Integer, Projeto> implements ProjectDao {

    @Override
    @Transactional
    public List<Projeto> findAll(boolean sorted, String sortField, String sortOrder, List<String> filterFields, List<String> filterValues, int from, int to) {
        Order order = (sortOrder.equals("asc")) ? Order.asc(sortField) : Order.desc(sortField);
        int max = Math.max(0, to - from);
        Criteria query = createEntityCriteria();
        if (filterFields.size() == filterValues.size())
            filterFields.stream()
                    .forEach(value -> query.createAlias(value, value).add(Restrictions.like(value, "%" + filterValues.get(filterFields.indexOf(value)) + "%")));
        if (sorted)
            query.addOrder(order);
        List<Projeto> uniqueSubList = query
                .setProjection(Projections.distinct(Projections.property("id_projeto")))
                .setFirstResult(from)
                .setMaxResults(max)
                .list();

        List<Projeto> projetos = !uniqueSubList.isEmpty() ? query
                .setFirstResult(0)
                .setMaxResults(Integer.MAX_VALUE)
                .setProjection(null)
                .add(Restrictions.in("id_projeto", uniqueSubList))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list()
                :
                new ArrayList<>();
        return projetos;
    }

    @Override
    public Long total(List<String> filterFields, List<String> filterValues) {
        Criteria query = createEntityCriteria().createAlias("responsavel", "responsavel").createAlias("membrosDaBanca", "membrosDaBanca").createAlias("autores", "autores");
        if (filterFields.size() == filterValues.size())
            filterFields.stream().forEach(value -> query.add(Restrictions.like(value, "%" + filterValues.get(filterFields.indexOf(value)) + "%")));
        return (Long) query.setProjection(Projections.countDistinct("id_projeto")).uniqueResult();
    }
}
