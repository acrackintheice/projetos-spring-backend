package hello.service;

import hello.model.Evento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by DU on 17/04/2017.
 */

public interface EventoService {

    List<Evento> findAll();

    List<Evento> findAll(boolean sorted, String sortField, String sortOrder, List<String> filterFields, List<String> filterValues, int from, int to);

}
