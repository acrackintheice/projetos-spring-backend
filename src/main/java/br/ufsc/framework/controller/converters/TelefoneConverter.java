package br.ufsc.framework.controller.converters;

import br.ufsc.framework.services.util.Strings;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.text.DecimalFormat;

public class TelefoneConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelefoneConverter.class);

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String s) {
        if (s == null || s.trim().equals(""))
            return null;

        String tel = s.trim().replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "");

        try {
            if (!tel.equals("") && NumberUtils.isNumber(tel))
                return new Long(tel);
            return null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return -1L;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object o) {
        if (o == null)
            return "";

        try {

            if (o.toString().length() <= 8 || o.toString().length() == 10) {
                return Strings.formatar(new DecimalFormat("0000000000").format(getAsObject(arg0, arg1, o.toString())),
                    "(##) ####-####");

            } else if (o.toString().length() == 11) {
                return Strings.formatar(new DecimalFormat("00000000000").format(getAsObject(arg0, arg1, o.toString())),
                    "(##) #####-####");
            } else {

                String mask = "(##) #####-####";
                for (int i = 11; i < o.toString().length(); i++) {
                    mask = mask + "#";
                }
                return Strings.formatar(new DecimalFormat("00000000000").format(getAsObject(arg0, arg1, o.toString())), mask);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return o.toString();
        }

    }

}
