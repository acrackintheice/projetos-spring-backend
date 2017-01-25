package hello.service;

import java.util.List;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.dao.CandidatoDao;
import hello.model.Candidato;

@Service("candidatoService")
@Transactional
public class CandidatoServiceImpl implements CandidatoService {

	@Autowired
	private CandidatoDao dao;
	
	public Candidato findById(int id) {
		return dao.findById(id);
	}

	public void save(Candidato Candidato) {
		dao.save(Candidato);
	}
	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with
	 * proper values within transaction. It will be updated in db once
	 * transaction ends.
	 */
	public void update(Candidato candidato) {
		Candidato entity = dao.findById(candidato.getId_candidato());
		
		if (entity != null) {
			entity.setNome(candidato.getNome());
			entity.setCpf(candidato.getCpf());
			entity.setUnidade_federativa(candidato.getUnidade_federativa());
			entity.setCidade(candidato.getCidade());
			entity.setBairro(candidato.getBairro());
			entity.setData_nascimento(candidato.getData_nascimento());
			entity.setAcertos_total(candidato.getAcertos_total());
			entity.setEvento(candidato.getEvento());
			}
	}

	public List<Candidato> findAll() {
		return dao.findAll();
	}

	public void delete(Candidato candidato) {
		dao.deleteByName(candidato.getNome());
	}

	@Override
	public List<Candidato> findByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public void deleteById(Candidato candidato) {
		dao.deleteById(candidato.getId_candidato());
	}

	@Override
	public Candidato findByCpf(String cpf) {
		return dao.findByCpf(cpf);
	}

	@Override
	public void deleteByCpf(String cpf) {
		dao.deleteByCpf(cpf);
	}

}
