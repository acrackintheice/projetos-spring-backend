package br.ufsc.framework.services.security.cas;

import org.jasig.cas.client.util.CommonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomCASAuthenticationEntryPoint implements AuthenticationEntryPoint, InitializingBean {

    private ServiceProperties serviceProperties;

    private String loginUrl;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(this.loginUrl, "loginUrl must be specified");
        Assert.notNull(this.serviceProperties, "serviceProperties must be specified");
        Assert.notNull(this.serviceProperties.getService(), "serviceProperties.getService() cannot be null.");
    }


    @Override
    public void commence(final HttpServletRequest servletRequest, final HttpServletResponse response, final AuthenticationException authenticationException) throws IOException,
        ServletException {

        final String urlEncodedService = createServiceUrl(servletRequest, response);

        String redirectUrl = createRedirectUrl(urlEncodedService);

        String additionalParameters = getAdditionalRedirectToCASLoginParameters(servletRequest);

        if (additionalParameters != null)
            redirectUrl += "&" + additionalParameters;

        redirectStrategy.sendRedirect(servletRequest, response, redirectUrl);

    }

    protected String createServiceUrl(final HttpServletRequest request, final HttpServletResponse response) {
        return CommonUtils.constructServiceUrl(null, response, this.serviceProperties.getService(), null, this.serviceProperties.getArtifactParameter(), false);
    }

    protected String createRedirectUrl(final String serviceUrl) {
        return CommonUtils.constructRedirectUrl(this.loginUrl, this.serviceProperties.getServiceParameter(), serviceUrl, this.serviceProperties.isSendRenew(), false);
    }

    public final String getLoginUrl() {
        return this.loginUrl;
    }

    public final ServiceProperties getServiceProperties() {
        return this.serviceProperties;
    }

    public final void setLoginUrl(final String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public final void setServiceProperties(final ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected String getAdditionalRedirectToCASLoginParameters(final HttpServletRequest servletRequest) {
        return null;
    }

}
