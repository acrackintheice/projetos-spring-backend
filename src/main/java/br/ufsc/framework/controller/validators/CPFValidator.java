package br.ufsc.framework.controller.validators;

import br.ufsc.framework.services.InternationalizationService;
import br.ufsc.framework.services.context.AppContext;
import br.ufsc.framework.services.util.Validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.DecimalFormat;

public class CPFValidator implements Validator {


    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

        if (value == null)
            return;

        if (/*value == null || */!(value instanceof String || (value instanceof Number))
            || !Validation.validateCPF((new DecimalFormat("00000000000")
            .format(new Long(value.toString()))))) {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                AppContext.getBean(InternationalizationService.class)
                    .getMessage("cpf_invalid"), null);

            throw new ValidatorException(msg);
        }
    }
}
