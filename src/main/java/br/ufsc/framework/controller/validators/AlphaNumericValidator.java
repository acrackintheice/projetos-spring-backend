package br.ufsc.framework.controller.validators;

import br.ufsc.framework.services.util.Validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


public class AlphaNumericValidator implements Validator {


    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

        if (!(value instanceof String) || !Validation.validateAlphaNumeric((String) value))

            throw new ValidatorException(new FacesMessage());

    }

}
