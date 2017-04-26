package hello.repositories;

import hello.model.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "links", path = "links")
public interface LinkRepository extends CrudRepository<Link, Integer> {
}
