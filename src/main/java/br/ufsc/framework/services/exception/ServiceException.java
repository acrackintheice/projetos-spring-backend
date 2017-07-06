package br.ufsc.framework.services.exception;

import java.util.ArrayList;
import java.util.List;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    private List<String> mensagens = new ArrayList<>();

    public ServiceException(String msg) {
        super(msg);
        mensagens.add(msg);
    }

    public ServiceException(List<String> mensagens) {
        super((mensagens != null && mensagens.size() > 0) ? mensagens.get(0) : null);
        this.mensagens.addAll(mensagens);
    }

    public List<String> getMensagens() {
        return mensagens;
    }

}
