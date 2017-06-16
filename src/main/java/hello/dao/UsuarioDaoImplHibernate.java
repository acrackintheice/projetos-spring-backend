package hello.dao;

import hello.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("usuarioDao")
@Transactional
public class UsuarioDaoImplHibernate extends HibernateAbstractListDao<Integer, Usuario> implements UsuarioDao {

    /**
     * @inheritDoc
     *
     * Implementação utilizando o Hibernate
     */
    @Override
    public List<Usuario> findAll(List<String> sortFields, List<String> sortOrders, List<String> filterFields, List<String> filterValues, int from, int to) {
        return super.findAll(sortFields, sortOrders, filterFields, filterValues, from, to, "id");
    }

    /**
     * @inheritDoc
     *
     * Implementação utilizando o Hibernate
     */
    @Override
    public Long total(List<String> filterFields, List<String> filterValues) {
        return super.total(filterFields, filterValues, "id");
    }
}