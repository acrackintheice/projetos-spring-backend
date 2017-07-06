package br.ufsc.framework.services;

public interface InternationalizationService {

    public String getMessage(String key);

    public String getMessage(String key, Object... args);

}
