package hello.service;

import java.util.List;

import hello.model.Evento;

public interface EventoService {

	Evento findById(int id);

	void save(Evento Evento);

	void deleteById(Evento Evento);

	void update(Evento Evento);

	List<Evento> findAll();
	
}
