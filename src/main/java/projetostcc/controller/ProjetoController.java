package projetostcc.controller;

import com.fasterxml.jackson.annotation.JsonView;
import projetostcc.model.Projeto;
import projetostcc.model.View;
import projetostcc.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @JsonView(View.Projeto.class)
    @RequestMapping(value = "/projetos", method = RequestMethod.GET)
    public List<Projeto> getProjetos(@RequestParam(required = false, defaultValue = "0", value = "from") String fromParam,
                                     @RequestParam(required = false, defaultValue = "0", value = "to") String toParam,
                                     @RequestParam(required = false, defaultValue = "") List<String> sortFields,
                                     @RequestParam(required = false, defaultValue = "") List<String> sortOrders,
                                     @RequestParam(required = false, defaultValue = "") List<String> filterFields,
                                     @RequestParam(required = false, defaultValue = "") List<String> filterValues) {

        List<Projeto> projetos = projetoService.findAll(
                fixFields(sortFields),
                sortOrders,
                fixFields(filterFields),
                filterValues,
                Integer.parseInt(fromParam),
                Integer.parseInt(toParam)
        );
        return projetos;
    }

    /**
     * Método que corrige o nome dos campos utilizados nas buscas
     *
     * @param fields
     *
     * @return uma lista com os filtros corrigidos
     */
    private List<String> fixFields(List<String> fields) {
        List<String> newFields = fields;
        if (fields.contains("membrosDaBanca")) {
            newFields.set(fields.indexOf("membrosDaBanca"), "membrosDaBanca.nome");
        }
        if (fields.contains("autores")) {
            newFields.set(fields.indexOf("autores"), "autores.nome");
        }
        return newFields;
    }

    @RequestMapping(value = "/projetos/total", method = RequestMethod.GET)
    public Number getTotalDeUsuarios(@RequestParam(required = false, defaultValue = "") List<String> filterFields,
                                     @RequestParam(required = false, defaultValue = "") List<String> filterValues) {
        return projetoService.total(fixFields(filterFields), filterValues);
    }
}
