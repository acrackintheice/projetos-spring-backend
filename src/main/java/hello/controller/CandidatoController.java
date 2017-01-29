package hello.controller;

import java.util.ArrayList;
import java.util.List;

import hello.model.TotalCandidatos;
import hello.util.CandidatoComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hello.model.Candidato;
import hello.service.CandidatoService;
import hello.service.EventoService;

@CrossOrigin
@RestController
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @Autowired
    private EventoService eventoService;

    /* [C]reate */
    @RequestMapping(value = "/candidatos", method = RequestMethod.POST)
    public String createCandidato(@RequestBody Candidato candidato) {
        candidato.setEvento(eventoService.findById(28));
        candidatoService.save(candidato);
        return "200";
    }

    /* [R]ead */
    @RequestMapping(value = "/candidatos", method = RequestMethod.GET)
    public List<Candidato> getCandidatos(@RequestParam(required = false, defaultValue = "0", value="from") String fromParam,
                                         @RequestParam(required = false, defaultValue = "0", value ="to") String toParam,
                                         @RequestParam(required = false, defaultValue = "false") boolean sorted,
                                         @RequestParam(required = false, defaultValue = "nome") String sortField,
                                         @RequestParam(required = false, defaultValue = "true") boolean asc) {

        int to = Integer.parseInt(toParam);
        int from = Integer.parseInt(fromParam);
        List<Candidato> candidatos = new ArrayList<>();
        try {
            candidatos = (sorted) ?  candidatoService.findAll(sortField, asc) : candidatoService.findAll();
            return (to == 0) ? candidatos : candidatos.subList(from, to);
        } catch (IndexOutOfBoundsException e) {
            return candidatos;
        }
    }

    @RequestMapping(value = "/candidatos/totalDeCandidatos", method = RequestMethod.GET)
    public TotalCandidatos getTotalDeCandidatos() {
        return new TotalCandidatos(candidatoService.findAll().size());
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
