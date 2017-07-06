package br.ufsc.framework.controller.backbeans.common;

import br.ufsc.framework.services.context.AppContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;

@Component
@Scope()
@Qualifier("systemBean")
public class SystemBean {

    public boolean isDesenvolvimento() {
        return AppContext.getProperty("database.isDesenvolvimento", Boolean.class);
    }

    public String getUrlAlterarSenha() {
        return AppContext.getProperty("cadastro.pessoa.alterarSenha.url", String.class);
    }

    public String getUrlAlterarEmail() {
        return AppContext.getProperty("cadastro.pessoa.alterarEmail.url", String.class);
    }

    public String getCtx() {

        String ctxPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        return ctxPath == null ? "" : ctxPath;
    }

}
