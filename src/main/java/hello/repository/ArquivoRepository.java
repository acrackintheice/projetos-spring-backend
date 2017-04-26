package hello.repository;

import hello.model.Arquivo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "arquivos", path = "arquivos")
public interface ArquivoRepository extends CrudRepository<Arquivo, Integer>{
}
