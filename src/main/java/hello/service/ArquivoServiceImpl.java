package hello.service;

import hello.dao.old.ArquivoDao;
import hello.model.Arquivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by DU on 19/04/2017.
 */

@Service("arquivoService")
@Transactional
class ArquivoServiceImpl implements ArquivoService {

    @Autowired
    private ArquivoDao arquivoDao;

    @Override
    public List<Arquivo> findAll() {
        return arquivoDao.findAll();
    }
}
