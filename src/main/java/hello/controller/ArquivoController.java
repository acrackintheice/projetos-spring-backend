package hello.controller;

import hello.model.Arquivo;
import hello.service.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by DU on 19/04/2017.
 */

@CrossOrigin
@RestController
public class ArquivoController {

    @Autowired
    private ArquivoService arquivoService;

    @RequestMapping(value = "/arquivos")
    public List<Arquivo> getArquivos() {
        return arquivoService.findAll();
    }


}
