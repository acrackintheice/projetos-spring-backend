package hello.service;

import java.util.List;

import hello.model.Candidato;

public interface CandidatoService {

	Candidato findById(int id);

	void save(Candidato Candidato);

	void delete(Candidato Candidato);
	
	void deleteById(Candidato Candidato);

	void update(Candidato Candidato);

	List<Candidato> findAll();

	List<Candidato> findAll(String sortField, boolean asc);
	
	List<Candidato> findByName(String name);
	
	Candidato findByCpf(String cpf);

	void deleteByCpf(String cpf);

}
