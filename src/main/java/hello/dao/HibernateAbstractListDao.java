package hello.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DU on 18/04/2017.
 */
public abstract class HibernateAbstractListDao<PK extends Serializable, T> extends HibernateAbstractDao<PK, T> {

    /**
     * Esse método retorna uma lista de T levando em consideração
     * os parâmetros de ordenação, filtros e limitação do resultado
     *
     * @param sorted Se o resultado deve ser ordenado ou não
     * @param sortField Qual campo deve ser utilizado para a ordenação
     * @param sortOrder Qual deve ser a forma de ordenação (ascendente ou descendente)
     * @param filterFields Uma lista com os campos de filtragem
     * @param filterValues Uma lista com os valores de filtragem
     * @param from Indice do primeiro registro
     * @param to Indice do ultimo registro
     * @param propriedadeDistinta propriedade utilizada para
     * filtrar os resultados repetidos objtidos após os joins
     * @return uma lista de T
     */
    public List<T> findAll(boolean sorted, String sortField, String sortOrder,
                           List<String> filterFields, List<String> filterValues,
                           int from, int to, String propriedadeDistinta) {
        Order order = (sortOrder.equals("asc")) ? Order.asc(sortField) : Order.desc(sortField);
        int max = Math.max(0, to - from);
        Criteria query = createEntityCriteria();
        if (filterFields.size() == filterValues.size()) {
            adicionarAliases(filterFields, query);
            adicionarRestricoes(filterFields, filterValues, query);
        }
        if (sorted)
            query.addOrder(order);

        List<T> uniqueSubList = query
                .setProjection(Projections.distinct(Projections.property(propriedadeDistinta)))
                .setFirstResult(from)
                .setMaxResults(max)
                .list();

        List<T> result = !uniqueSubList.isEmpty() ? query
                .setFirstResult(0)
                .setMaxResults(Integer.MAX_VALUE)
                .setProjection(null)
                .add(Restrictions.in(propriedadeDistinta, uniqueSubList))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list()
                :
                new ArrayList<>();
        return result;
    }

    public Long total(List<String> filterFields, List<String> filterValues, String propriedadeDistinta) {
        Criteria query = createEntityCriteria();
        if (filterFields.size() == filterValues.size()){
            adicionarAliases(filterFields, query);
            adicionarRestricoes(filterFields, filterValues, query);
        }
        return (Long) query.setProjection(Projections.countDistinct(propriedadeDistinta)).uniqueResult();
    }

    private void adicionarRestricoes(List<String> filterFields, List<String> filterValues, Criteria query) {
        filterFields.stream()
                .forEach(value -> query.add(Restrictions.like(value, "%" + filterValues.get(filterFields.indexOf(value)) + "%")));
    }

    private void adicionarAliases(List<String> filterFields, Criteria query) {
        filterFields.stream().filter(value -> value.split("\\.").length > 1)
                .forEach(value -> query.createAlias(getAlias(value), getAlias(value)));
    }

    private String getAlias(String field) {
        try {
            return field.split("\\.")[0];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Field não era splitavel, algo deu errado");
            return null;
        }
    }

}
