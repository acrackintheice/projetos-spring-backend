package projetostcc.service;

import projetostcc.dao.ProjetoDao;
import projetostcc.model.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("projetoService")
@Transactional
public class ProjetoServiceImpl implements ProjetoService {


    @Autowired
    private ProjetoDao dao;

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
    public List<Projeto> findAll(List<String> sortFields, List<String> sortOrders, List<String> filterFields, List<String> filterValues, int from, int to) {
        return dao.findAll(sortFields, sortOrders, filterFields, filterValues, from, to);
    }
}
