package hello.dao;

import hello.model.Usuario;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by DU on 13/04/2017.
 */
public interface UsuarioDao {

    Usuario findByIdUfsc(Long idUfsc);

    Usuario findByMatricula(Long matricula);

    List<Usuario> findAllByProjeto(Integer idProjeto);

    @Transactional
    List<Usuario> findAll(boolean sorted, String sortField, String sortOrder, List<String> filterFields, List<String> filterValues, int from, int to);

    Long total(List<String> filterFields, List<String> filterValues);
}
