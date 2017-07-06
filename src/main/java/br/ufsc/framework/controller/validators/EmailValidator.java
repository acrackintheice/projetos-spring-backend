package br.ufsc.framework.controller.validators;

import br.ufsc.framework.services.InternationalizationService;
import br.ufsc.framework.services.context.AppContext;
import br.ufsc.framework.services.util.Emails;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {


    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value != null && !value.toString().isEmpty() && !Emails.validarEmail(value.toString())) {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, AppContext.getBean(InternationalizationService.class).getMessage(
                "erro_email_invalido"), null);

            throw new ValidatorException(msg);
        }
    }
}
