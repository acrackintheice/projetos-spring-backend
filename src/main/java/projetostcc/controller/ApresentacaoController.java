package projetostcc.controller;


import com.fasterxml.jackson.annotation.JsonView;
import projetostcc.model.Apresentacao;
import projetostcc.model.View;
import projetostcc.repository.ApresentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class ApresentacaoController {

    @Autowired
    private ApresentacaoRepository repository;


    @JsonView(View.Projeto.class)
    @RequestMapping(value = "/apresentacoes")
    public List<Apresentacao> getApresentacoes(){
        List<Apresentacao> list = new ArrayList<>();
        repository.findAll().forEach(list::add);;
        return list;
    }


}
