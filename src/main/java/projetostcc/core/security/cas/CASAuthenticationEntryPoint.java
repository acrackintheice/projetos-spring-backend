package projetostcc.core.security.cas;

import br.ufsc.framework.services.security.cas.CustomCASAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;

public class CASAuthenticationEntryPoint extends CustomCASAuthenticationEntryPoint {

    @Override
    protected String getAdditionalRedirectToCASLoginParameters(HttpServletRequest servletRequest) {
        return "userType=padrao&lockUserType=1";
    }

}
