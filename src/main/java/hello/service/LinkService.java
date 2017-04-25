package hello.service;

import hello.model.Link;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DU on 22/04/2017.
 */


public interface LinkService {

    List<Link> findAll();

}
