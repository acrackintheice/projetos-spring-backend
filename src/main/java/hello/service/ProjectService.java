package hello.service;

import hello.model.Projeto;

import java.util.List;

/**
 * Created by DU on 14/04/2017.
 */
public interface ProjectService {

    Long total(List<String> filterFields, List<String> filterValues);

    List<Projeto> findAll(boolean sorted, String sortField, String sortOrder, List<String> filterFields, List<String> filterValues, int from, int to);
}
