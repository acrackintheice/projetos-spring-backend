package hello.repositories;

import hello.model.Arquivo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "arquivos", path = "arquivos")
public interface ArquivoRepository extends CrudRepository<Arquivo, Integer>{
}
