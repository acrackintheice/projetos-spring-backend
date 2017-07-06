package br.ufsc.framework.services.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomSessionFixationProtectionFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSessionFixationProtectionFilter.class);

    private SessionAuthenticationStrategy sessionStrategy = new SessionFixationProtectionStrategy();

    private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    @Autowired
    private HttpSessionSecurityContextRepository securityContextRepository;


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (session != null && session.getAttribute("SPRING_SECURITY_SESSION_FIXATION_PROTECTION_APPLIED") == null
            && authentication != null && !authenticationTrustResolver.isAnonymous(authentication)) {

            session.setAttribute("SPRING_SECURITY_SESSION_FIXATION_PROTECTION_APPLIED", true);

            try {
                sessionStrategy.onAuthentication(authentication, request, response);
                securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage(), e);
            }
        }
        chain.doFilter(request, response);
    }

    public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionStrategy) {
        Assert.notNull(sessionStrategy, "authenticatedSessionStratedy must not be null");
        this.sessionStrategy = sessionStrategy;
    }

    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return authenticationTrustResolver;
    }

}
