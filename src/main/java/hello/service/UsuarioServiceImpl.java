package hello.service;

import hello.dao.UsuarioDao;
import hello.model.Projeto;
import hello.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("usuarioService")
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao dao;

    /**
     * @inheritDoc
     *
     * Implementação que acessa um banco de dados relacional
     */
    @Override
    public Long total(List<String> filterFields, List<String> filterValues) {
        return dao.total(filterFields, filterValues);
    }

    /**
     * @inheritDoc
     *
     * Implementação que acessa um banco de dados relacional
     */
    @Override
    public List<Usuario> findAll( List<String> sortFields, List<String> sortOrders, List<String> filterFields, List<String> filterValues, int from, int to) {
        return dao.findAll(sortFields, sortOrders, filterFields, filterValues, from, to);
    }
}