package hello.repositories;

import hello.model.Evento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "eventos", path = "eventos")
public interface EventoRepository extends CrudRepository<Evento, Integer>{
}
