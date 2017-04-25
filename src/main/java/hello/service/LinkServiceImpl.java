package hello.service;

import hello.dao.LinkDao;
import hello.model.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by DU on 22/04/2017.
 */

@Service("linkService")
@Transactional
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDao linkDao;

    @Override
    public List<Link> findAll() {
        return linkDao.findAll();
    }
}
