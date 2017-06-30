package projetostcc.repository;

import projetostcc.model.Duvida;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "duvidas", path = "duvidas")
public interface DuvidaRepository extends CrudRepository<Duvida, Integer> {

}
