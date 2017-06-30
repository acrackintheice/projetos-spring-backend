package projetostcc.repository;

import projetostcc.model.Evento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "eventos", path = "eventos")
public interface EventoRepository extends CrudRepository<Evento, Integer>{
}
