package br.ufsc.framework.controller.backbeans.common;

import br.ufsc.framework.controller.util.FacesUtil;
import br.ufsc.framework.services.InternationalizationService;
import br.ufsc.framework.services.context.AppContext;
import projetostcc.core.security.cas.ProjetosSecurityContext;
import projetostcc.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Scope("request")
@Qualifier("loginBean")
public class LoginBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginBean.class);

    @Autowired
    InternationalizationService i18n;
    private String message;


    @SuppressWarnings("unused")
    private static final String casLogoutURL = AppContext.getProperty("auth.cas.server.logout.url", String.class);

    @PostConstruct
    public void setup() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

            HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();

            if ((session == null) || (session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") == null) || (req.getParameter("logout") != null))
                return;
            this.message = this.i18n.getMessage("login_invalid_data");
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public String getLogoutURL() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            return FacesUtil.getFullUrl(context, "j_spring_cas_security_logout", true);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return FacesUtil.getFullUrl(context, "j_spring_security_logout");
        }

    }

    public String getMessage() {
        return this.message;
    }

    public Usuario getLoggedUser() {
        return ProjetosSecurityContext.getCurrentUser();
    }

    public boolean isCurrentUserAuthenticated() {
        return ProjetosSecurityContext.isCurrentUserAuthenticated();
    }

    public boolean userHasRole(String role) {
        return ProjetosSecurityContext.currentUserHasRole(role);
    }

}
