package hello.dao;

import hello.model.Projeto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("projetoDao")
public class ProjetoDaoImplHibernate extends HibernateAbstractListDao<Integer, Projeto> implements ProjetoDao {

    /**
     * @inheritDoc
     *
     * Implementação utilizando o Hibernate
     */
    @Override
    public List<Projeto> findAll(List<String> sortFields, List<String> sortOrders, List<String> filterFields, List<String> filterValues, int from, int to) {
        return super.findAll(sortFields, sortOrders, filterFields, filterValues, from, to, "id_projeto");
    }

    /**
     * @inheritDoc
     *
     * Implementação utilizando o Hibernate
     */
    @Override
    public Long total(List<String> filterFields, List<String> filterValues) {
        return super.total(filterFields, filterValues, "id_projeto");
    }
}
