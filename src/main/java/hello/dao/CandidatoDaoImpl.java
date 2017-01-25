package hello.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hello.model.Candidato;

@Repository("candidatoDao")
public class CandidatoDaoImpl extends AbstractDao<Integer, Candidato> implements CandidatoDao {

	public Candidato findById(int id) {
		return getByKey(id);
	}

	public void save(Candidato candidato) {
		//persist(candidato);
		getSession().merge(candidato);
	}

	public void deleteById(int id) {
		Query query = getSession().createSQLQuery("delete from candidato where id_candidato = :id_candidato");
		query.setInteger("id_candidato", id);
		query.executeUpdate();
	}

	public void deleteByName(String name) {
		Query query = getSession().createSQLQuery("delete from candidato where name = :name");
		query.setString("name", name);
		query.executeUpdate();
	}

	
	@SuppressWarnings("unchecked")
	public List<Candidato> findAll() {
		return createEntityCriteria().list();
	}

	@SuppressWarnings("unchecked")
	public List<Candidato> findByName(String nome) {
		return createEntityCriteria().add(Restrictions.like("nome", nome)).list();
	}

	@Override
	public Candidato findByCpf(String cpf) {
		try{
			return (Candidato) createEntityCriteria().add(Restrictions.like("cpf", cpf)).list().get(0);
		}
		catch(Exception NullPointerException){
			return null;
		}
	}

	public void deleteByCpf(String cpf) {
		Query query = getSession().createSQLQuery("delete from candidato where cpf = :cpf");
		query.setString("cpf", cpf);
		query.executeUpdate();
	}

}
