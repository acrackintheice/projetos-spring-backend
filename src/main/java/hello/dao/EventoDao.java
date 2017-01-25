package hello.dao;

import java.util.List;

import hello.model.Evento;

public interface EventoDao {

	Evento findById(int id);

	void save(Evento client);

	void deleteById(int id);

	List<Evento> findAll();

}
