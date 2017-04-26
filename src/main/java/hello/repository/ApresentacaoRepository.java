package hello.repository;

import hello.model.Apresentacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Created by eduardo on 26/04/17.
 */
@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "apresentacoes", path = "apresentacoes")
public interface ApresentacaoRepository extends CrudRepository<Apresentacao, Long> {
}
