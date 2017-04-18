package hello.service;

import hello.model.Usuario;

import javax.transaction.Transactional;
import java.util.List;

public interface UsuarioService {

    Long total(List<String> filterFields, List<String> filterValues);

    @Transactional
    List<Usuario> findAll(boolean sorted, String sortField, String sortOrder, List<String> filterFields, List<String> filterValues, int from, int to);

}
