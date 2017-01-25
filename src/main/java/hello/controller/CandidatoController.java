package hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.model.Candidato;
import hello.service.CandidatoService;
import hello.service.EventoService;

@RestController
public class CandidatoController {

	@Autowired
	CandidatoService candidatoService;
	
	@Autowired
	EventoService eventoService;

	/* [C]reate */
	@RequestMapping(value = "/candidatos", method = RequestMethod.POST)
	public String createCandidato(@RequestBody Candidato candidato) {
		candidato.setEvento(eventoService.findById(28));
		candidatoService.save(candidato);
		return "200";
	}

	/* [R]ead */
	@RequestMapping(value = "/candidatos", method = RequestMethod.GET)
	public List<Candidato> getCandidatos() {
		return candidatoService.findAll();
	}

	@RequestMapping(value = "/candidatos/{cpf}", method = RequestMethod.GET)
	public Candidato getCandidato(@PathVariable String cpf) {
		return candidatoService.findByCpf(cpf);
	}

	/* [U]pdate */
	@RequestMapping(value = "/candidatos/{cpf}", method = RequestMethod.POST)
	public String updateCandidato(@PathVariable String cpf, @RequestBody Candidato newCandidato) {
		Candidato oldCandidato = candidatoService.findByCpf(cpf);
		if (oldCandidato != null) {
			newCandidato.setId_candidato(oldCandidato.getId_candidato());
			candidatoService.update(newCandidato);
			return "200";
		} else
			return "400";
	}

	/* [D]elete */
	@RequestMapping(value = "/candidatos/{cpf}", method = RequestMethod.DELETE)
	public String deleteCandidato(@PathVariable String cpf) {
		Candidato candidato = candidatoService.findByCpf(cpf);
		if (candidato != null) {
			candidatoService.deleteByCpf(cpf);
			return "200";
		} else
			return "400";
	}
}
