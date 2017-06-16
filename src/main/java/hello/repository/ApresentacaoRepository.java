package hello.repository;

import hello.model.Apresentacao;
import org.springframework.data.repository.CrudRepository;

public interface ApresentacaoRepository extends CrudRepository<Apresentacao, Long> {
}
