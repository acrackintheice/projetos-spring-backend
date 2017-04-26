package hello.controller.old;

import hello.model.Link;
import hello.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by DU on 22/04/2017.
 */

@CrossOrigin
@RestController
public class LinkController {

    @Autowired
    private LinkService linkService;

    @RequestMapping(value = "/links")
    public List<Link> getLinks(){
        return linkService.findAll();
    }

}
