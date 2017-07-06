package br.ufsc.framework.controller.converters;

import br.ufsc.framework.services.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.text.DecimalFormat;

public class CEPConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CEPConverter.class);

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String s) {
        if (s == null || s.trim().equals(""))
            return null;

        try {
            return new Integer(s.trim().replaceAll("-", ""));
        } catch (Exception e) {
            return -1L;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object o) {
        if (o == null)
            return "";

        try {

            return Strings.formatar(new DecimalFormat("00000000").format(getAsObject(arg0, arg1, o.toString())),
                "#####-###");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return o.toString();
        }

    }

}
