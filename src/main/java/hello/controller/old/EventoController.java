package hello.controller.old;

import com.fasterxml.jackson.annotation.JsonView;
import hello.model.Evento;
import hello.model.View;
import hello.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @RequestMapping(value = "/eventos", method = RequestMethod.GET)
    public List<Evento> getEventos() {
        return eventoService.findAll();
    }
}
