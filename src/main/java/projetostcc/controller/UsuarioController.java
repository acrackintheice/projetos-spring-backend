package projetostcc.controller;

import com.fasterxml.jackson.annotation.JsonView;
import projetostcc.model.*;
import projetostcc.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @JsonView(View.Usuario.class)
    @RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    public List<Usuario> getCandidatos(@RequestParam(required = false, defaultValue = "0", value = "from") String fromParam,
                                       @RequestParam(required = false, defaultValue = "0", value = "to") String toParam,
                                       @RequestParam(required = false, defaultValue = "") List<String> sortFields,
                                       @RequestParam(required = false, defaultValue = "") List<String> sortOrders,
                                       @RequestParam(required = false, defaultValue = "") List<String> filterFields,
                                       @RequestParam(required = false, defaultValue = "") List<String> filterValues) {
        List<Usuario> usuarios = usuarioService.findAll(
                sortFields,
                sortOrders,
                filterFields,
                filterValues,
                Integer.parseInt(fromParam),
                Integer.parseInt(toParam)
        );
        return usuarios;
    }

    @RequestMapping(value = "/usuarios/total", method = RequestMethod.GET)
    public Number getTotalDeUsuarios(@RequestParam(required = false, defaultValue = "") List<String> filterFields,
                                     @RequestParam(required = false, defaultValue = "") List<String> filterValues) {
        return usuarioService.total(filterFields, filterValues);
    }
}
