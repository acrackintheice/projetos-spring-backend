package hello.repositories;

import hello.model.Duvida;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "duvidas", path = "duvidas")
public interface DuvidaRepository extends CrudRepository<Duvida, Integer> {

}
