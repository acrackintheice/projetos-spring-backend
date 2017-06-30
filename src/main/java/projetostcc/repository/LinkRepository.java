package projetostcc.repository;

import projetostcc.model.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "links", path = "links")
public interface LinkRepository extends CrudRepository<Link, Integer> {
}
