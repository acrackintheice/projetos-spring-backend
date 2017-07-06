package projetostcc.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
     * @param tipo                String com o nome do tipo T
     * @return uma lista de T
     */
    public List<T> findAll(List<String> sortFields,
                           List<String> sortOrders,
                           List<String> filterFields,
                           List<String> filterValues,
                           int from,
                           int to,
                           String propriedadeDistinta,
                           String tipo
    ) {
        Criteria query = createEntityCriteria();

        /* Adicionando aliases */
        adicionarAliases(aliasesFromFields(sortFields, filterFields), query);

        /* Adicionando filtros */
        if (filterValues.size() == filterFields.size())
            adicionarRestricoes(filterFields, filterValues, query);

        /* Adicionando Ordenação */
        if (sortFields.size() == sortOrders.size()) {
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
                .setMaxResults(Math.max(0, to - from))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();

        /* Agora os valores obtidos na etapa anterior são utilziados para realizar uma segundo busca no banco e obter o resultado final
           OBS: Foi utilizado HQL em vez de Criteria nesta query porque a Criteria API não suporta a função "order by FIELD" do Mysql
           */
        List<T> result = new ArrayList<>();
        if (!uniqueSubList.isEmpty()) {
            List<String> stringSublist = uniqueSubList.stream().map(x -> x.toString()).collect(Collectors.toList());
            String orderByField = " order by field(" + propriedadeDistinta + ", " + String.join(",", stringSublist) + ") DESC";
            String whereIn = " where " + propriedadeDistinta + " in (" + String.join(",", stringSublist) + ")";
            String hqlQueryString = "FROM " + tipo + whereIn + orderByField;
            Query hqlQuery = getSession().createQuery(hqlQueryString);
            result = hqlQuery.list();
            Collections.reverse(result);
        }
        return result;
/*
        Criteria secondQuery = createEntityCriteria();
        List<T> result =
                !uniqueSubList.isEmpty() ? secondQuery
                        .add(Restrictions.in(propriedadeDistinta, uniqueSubList)) // Filtrando com base nos valores obtidos na etapa anterior
                        .list()
                        :
                        new ArrayList<>();
*/
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

    private List<String> aliasesFromFields(List<String> sortFields, List<String> filterFields) {
        List<String> allFields = new ArrayList<>();
        allFields.addAll(sortFields);
        allFields.addAll(filterFields);
        return allFields.stream().distinct().collect(Collectors.toList());
    }

}