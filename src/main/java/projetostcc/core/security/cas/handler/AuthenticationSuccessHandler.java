package projetostcc.core.security.cas.handler;

import projetostcc.core.security.cas.ProjetosSecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        boolean isRedirecionar = ProjetosSecurityContext.currentUserHasRole("ROLE_REDIRECIONAMENTO_CAS");

        if (isRedirecionar) {
            getRedirectStrategy().sendRedirect(request, response, "/authGateway");
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
