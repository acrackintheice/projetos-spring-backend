package br.ufsc.framework.controller.converters;

import br.ufsc.framework.services.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.text.DecimalFormat;

public class CPFConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CPFConverter.class);

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String s) {
        if (s == null || s.trim().equals(""))
            return null;

        try {
            return new Long(s.trim().replaceAll("\\.", "").replaceAll("-", ""));
        } catch (Exception e) {
            return -1L;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object o) {
        if (o == null || o.toString().trim().equals("") || o.toString().trim().equals("0"))
            return "";

        try {

            return Strings.formatar(new DecimalFormat("00000000000").format(getAsObject(arg0, arg1, o.toString())), "###.###.###-##");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return o.toString();
        }

    }

    private static CPFConverter instance = null;

    public static String format(Long l) {
        if (instance == null)
            instance = new CPFConverter();

        return instance.getAsString(null, null, l);

    }

    public static Long parse(String s) {
        if (instance == null)
            instance = new CPFConverter();

        return (Long) instance.getAsObject(null, null, s);

    }

}
