package projetostcc.core.security.cas.handler;

import projetostcc.core.security.cas.ProjetosSecurityContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedHandler extends AccessDeniedHandlerImpl {

    public AccessDeniedHandler() {
        setErrorPage("/erros/denied.xhtml");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (ProjetosSecurityContext.currentUserHasRole("ROLE_REDIRECIONAMENTO_CAS")) {
            if (!response.isCommitted()) {
                response.sendRedirect(request.getContextPath() + "/authGateway");
            }
        } else {
            super.handle(request, response, accessDeniedException);
        }
    }
}
