package hello.dao;

import java.util.List;

import hello.model.Candidato;

public interface CandidatoDao {

	Candidato findById(int id);

	List<Candidato> findByName(String name);
	
	Candidato findByCpf(String cpf);

	void save(Candidato client);

	void deleteById(int id);

	void deleteByName(String name);

	List<Candidato> findAll();

	List<Candidato> findAll(String sortField, boolean asc);

	void deleteByCpf(String cpf);

}
