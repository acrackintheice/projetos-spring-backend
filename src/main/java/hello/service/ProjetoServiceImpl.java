package hello.service;

import hello.dao.ProjetoDao;
import hello.model.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("projetoService")
@Transactional
public class ProjetoServiceImpl implements ProjetoService {


    @Autowired
    private ProjetoDao dao;

    @Override
    public Long total(List<String> filterFields, List<String> filterValues) {
        fixFilters(filterFields);
        return dao.total(filterFields, filterValues);
    }

    @Override
    public List<Projeto> findAll(boolean sorted, String sortField, String sortOrder, List<String> filterFields, List<String> filterValues, int from, int to) {
        List<Projeto> projetos;
        fixFilters(filterFields);
        projetos = dao.findAll(sorted, sortField, sortOrder, filterFields, filterValues, from, to);
        return projetos;
    }

    private void fixFilters(List<String> filterFields){
        if (filterFields.contains("membrosDaBanca")) {
            filterFields.set(filterFields.indexOf("membrosDaBanca"), "membrosDaBanca.nome");
        }
        if (filterFields.contains("autores")) {
            filterFields.set(filterFields.indexOf("autores"), "autores.nome");
        }
    }
}
