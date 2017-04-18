package hello.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hello.model.Projeto;
import hello.model.View;
import hello.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@Transactional
public class ProjetoController {

    @Autowired
    private ProjectService projetoService;

    @JsonView(View.Simples.class)
    @RequestMapping(value = "/projetos", method = RequestMethod.GET)
    public List<Projeto> getProjetos(@RequestParam(required = false, defaultValue = "0", value = "from") String fromParam,
                                       @RequestParam(required = false, defaultValue = "0", value = "to") String toParam,
                                       @RequestParam(required = false, defaultValue = "false") boolean sorted,
                                       @RequestParam(required = false, defaultValue = "titulo") String sortField,
                                       @RequestParam(required = false, defaultValue = "true") String sortOrder,
                                       @RequestParam(required = false, defaultValue = "") List<String> filterFields,
                                       @RequestParam(required = false, defaultValue = "") List<String> filterValues) {

        int to = Integer.parseInt(toParam);
        int from = Integer.parseInt(fromParam);
        List<Projeto> projetos = projetoService.findAll(sorted, sortField, sortOrder, filterFields, filterValues, from, to);
        return projetos;
    }

    @RequestMapping(value = "/projetos/total", method = RequestMethod.GET)
    public Number getTotalDeUsuarios(@RequestParam(required = false, defaultValue = "") List<String> filterFields,
                                     @RequestParam(required = false, defaultValue = "") List<String> filterValues) {
        return projetoService.total(filterFields, filterValues);
    }
}
