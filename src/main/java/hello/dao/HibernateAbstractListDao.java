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
     * Método que retorna uma lista de Ts levando em consideração
     * os parâmetros de ordenação, filtros e limitação do resultado
     *
     * @param sortFields          Campos de ordenação
     * @param sortOrders          Quais devem ser as formas de ordenação para cada campo (ascendente ou descendente)
     * @param filterFields        Uma lista com os campos de filtragem
     * @param filterValues        Uma lista com os valores de filtragem
     * @param from                Indice do primeiro registro do resultado a ser retornado
     * @param to                  Indice do ultimo registro registro do resultado a ser retornado
     * @param propriedadeDistinta propriedade utilizada para filtrar os resultados repetidos objtidos após os joins
     * @return uma lista de T
     */
    public List<T> findAll(List<String> sortFields,
                           List<String> sortOrders,
                           List<String> filterFields,
                           List<String> filterValues,
                           int from,
                           int to,
                           String propriedadeDistinta) {
        Criteria query = createEntityCriteria();
        Criteria secondQuery = createEntityCriteria();

        int max = Math.max(0, to - from);

        /* Se os nome dos campos de filtragem e os valores forem diferentes algo deu errado e nada será filtrado
        caso contrário, filtros serão adicionados a query */
        if (filterFields.size() == filterValues.size()) {
            adicionarAliases(filterFields, query);
            adicionarRestricoes(filterFields, filterValues, query);
        }

        if (!sortFields.isEmpty()) {
            adicionarOrders(sortFields, sortOrders, query);
            query.addOrder(Order.desc(propriedadeDistinta));
        }

        /* Para as coisas funcionarem direito, a busca precisou ser dividida em duas etapas, pois
         * quando a busca é realizada, os joins fazem com que vários resultados repetidos apareçam */

        /*  Na primeira etapa a busca é realizada é são selecionados alguns valores distintos para o atributo
            definido pelo parametro propriedadesDistintas. O número de valores selecionados depende dos parametros
           to e from      */
        List<T> uniqueSubList = query
                .setProjection(Projections.distinct(Projections.property(propriedadeDistinta)))
                .setFirstResult(from)
                .setMaxResults(max)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();

        /* Agora os valores obtidos na etapa anterior são utilziados para realizar uma segundo busca no banco e obter o resultado final
           OBS: A maior parte dos métodos invocados abaixo servem apenas para 'resetar' a query para seus valores padrões.
           */
        List<T> result =
                !uniqueSubList.isEmpty() ? secondQuery
                        .add(Restrictions.in(propriedadeDistinta, uniqueSubList)) // Filtrando com base nos valores obtidos na etapa anterior
                        .list()
                        :
                        new ArrayList<>();

        return result;
    }

    /**
     * Método que retorna o total de Ts que satisfazem * os parâmetros de filtragem
     *
     * @param filterFields        Uma lista com os campos de filtragem
     * @param filterValues        Uma lista com os valores de filtragem
     * @param propriedadeDistinta propriedade utilizada para filtrar os resultados repetidos objtidos após os joins
     * @return uma lista de T
     */
    public Long total(List<String> filterFields, List<String> filterValues, String propriedadeDistinta) {
        Criteria query = createEntityCriteria();
        if (filterFields.size() == filterValues.size()) {
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
            return "";
        }
    }

    private void adicionarOrders(List<String> sortFields, List<String> sortOrders, Criteria query) {
        sortFields.stream().forEach(order -> query.addOrder(getOrder(order, sortFields, sortOrders)));
    }

    private Order getOrder(String field, List<String> sortFields, List<String> sortOrders) {
        String sortOrder = sortOrders.get(sortFields.indexOf(field));
        return (sortOrder.equals("asc")) ? Order.asc(field) : Order.desc(field);
    }

}