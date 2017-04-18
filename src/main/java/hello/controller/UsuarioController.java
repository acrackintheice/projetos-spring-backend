package hello.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hello.model.*;
import hello.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @JsonView(View.Simples.class)
    @RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    public List<Usuario> getCandidatos(@RequestParam(required = false, defaultValue = "0", value = "from") String fromParam,
                                       @RequestParam(required = false, defaultValue = "0", value = "to") String toParam,
                                       @RequestParam(required = false, defaultValue = "false") boolean sorted,
                                       @RequestParam(required = false, defaultValue = "nome") String sortField,
                                       @RequestParam(required = false, defaultValue = "asc") String sortOrder,
                                       @RequestParam(required = false, defaultValue = "") List<String> filterFields,
                                       @RequestParam(required = false, defaultValue = "") List<String> filterValues) {
        int to = Integer.parseInt(toParam);
        int from = Integer.parseInt(fromParam);
        List<Usuario> usuarios = usuarioService.findAll(sorted, sortField, sortOrder, filterFields, filterValues, from, to);
        return usuarios;
    }

    @RequestMapping(value = "/usuarios/total", method = RequestMethod.GET)
    public Number getTotalDeUsuarios(@RequestParam(required = false, defaultValue = "") List<String> filterFields,
                                     @RequestParam(required = false, defaultValue = "") List<String> filterValues) {
        return usuarioService.total(filterFields, filterValues);
    }
}
