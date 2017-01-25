package hello.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hello.model.Evento;

@Repository("eventoDao")
public class EventoDaoImpl extends AbstractDao<Integer, Evento> implements EventoDao {

	public Evento findById(int id) {
		return getByKey(id);
	}

	public void save(Evento evento) {
		persist(evento);
	}

	public void deleteById(int id) {
		Query query = getSession().createSQLQuery("delete from evento where id_evento = :id_evento");
		query.setInteger("id_evento", id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Evento> findAll() {
		return createEntityCriteria().list();
	}

}
