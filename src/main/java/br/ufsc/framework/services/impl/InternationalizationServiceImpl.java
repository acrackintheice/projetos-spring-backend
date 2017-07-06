package br.ufsc.framework.services.impl;

import br.ufsc.framework.services.InternationalizationService;
import br.ufsc.framework.services.factory.ResourceBundleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ResourceBundle;

@Service
public class InternationalizationServiceImpl implements
    InternationalizationService {

    private ResourceBundle resources;
    private MessageFormat formatter;

    @Autowired
    public InternationalizationServiceImpl(ResourceBundleFactory factory) {
        resources = factory.getResourceBundle();
        formatter = new MessageFormat("");
        formatter.setLocale(resources.getLocale());
    }


    @Override
    public String getMessage(String key, Object... args) {
        if (args != null && args.length > 0)
            return MessageFormat.format(getMessage(key), args);
        else
            return getMessage(key);
    }


    @Override
    public String getMessage(String key) {
        try {
            return resources.getString(key);
        } catch (Exception e) {
            return key;
        }
    }
}
