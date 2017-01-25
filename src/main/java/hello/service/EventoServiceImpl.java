package hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.dao.EventoDao;
import hello.model.Evento;

@Service("eventoService")
@Transactional
public class EventoServiceImpl implements EventoService {

	@Autowired
	private EventoDao dao;
	
	@Override
	public Evento findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void save(Evento Evento) {
		dao.save(Evento);
	}
	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with
	 * proper values within transaction. It will be updated in db once
	 * transaction ends.
	 */
	@Override
	public void update(Evento evento) {
		Evento entity = dao.findById(evento.getId_evento());
		
		if (entity != null) {
			entity.setAcerto_minimo_exigido(evento.getAcerto_minimo_exigido());
			entity.setAno_evento(evento.getAno_evento());
			entity.setDescricao_evento(evento.getDescricao_evento());
			entity.setNumero_vagas(evento.getNumero_vagas());
			entity.setNumero_inscritos(evento.getNumero_inscritos());
			entity.setMedia_acertos(evento.getMedia_acertos());
			}
	}

	@Override
	public List<Evento> findAll() {
		return dao.findAll();
	}

	@Override
	public void deleteById(Evento evento) {
		dao.deleteById(evento.getId_evento());
	}

}
