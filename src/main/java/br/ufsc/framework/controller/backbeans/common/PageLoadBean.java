package br.ufsc.framework.controller.backbeans.common;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

@Component
@Scope("request")
@Qualifier("pageLoadBean")
public class PageLoadBean {

    public void preRenderEvt(ComponentSystemEvent event) throws Exception {

        //executar essa linha para evitar o problema: "Cannot create a session after the response has been committed"
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

}
