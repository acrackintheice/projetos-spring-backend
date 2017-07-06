package br.ufsc.framework.controller.validators;

import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class LuceneQueryValidator implements Validator {


    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value != null && value instanceof String && !value.toString().trim().isEmpty())
            try {
                QueryParser parser = new QueryParser(Version.LUCENE_33, "nome", new KeywordAnalyzer());
                parser.parse(value.toString());
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "A query informada é inválida", null);
                throw new ValidatorException(msg);
            }
    }
}
