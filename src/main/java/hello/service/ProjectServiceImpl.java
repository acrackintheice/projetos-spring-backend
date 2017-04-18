package hello.service;

import hello.dao.ProjectDao;
import hello.model.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("projetoService")
@Transactional
public class ProjectServiceImpl implements ProjectService {


    @Autowired
    private ProjectDao dao;

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
        /*System.out.println(projetos.get(0).getMembrosDaBanca().size());
        System.out.println(projetos.get(0).getAutores().size());
        System.out.println(projetos.get(0).getOrientadores().size());
        System.out.println(projetos.get(0).getCoorientadores().size());
        System.out.println(projetos.get(0).getResponsavel().getNome());*/
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
