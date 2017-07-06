package br.ufsc.framework.controller.validators;

import br.ufsc.framework.services.InternationalizationService;
import br.ufsc.framework.services.context.AppContext;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@FacesValidator("dateValidator")
public class DateValidator implements Validator {


    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value != null && value instanceof Date) {

            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime((Date) value);

            if ((gc.get(Calendar.YEAR) + "").length() < 4 || gc.get(Calendar.YEAR) < 1900 || gc.get(Calendar.YEAR) > 9999)
                throw new ValidatorException(new FacesMessage(AppContext.getBean(InternationalizationService.class).getMessage("date_invalid")));

        }
    }
}
