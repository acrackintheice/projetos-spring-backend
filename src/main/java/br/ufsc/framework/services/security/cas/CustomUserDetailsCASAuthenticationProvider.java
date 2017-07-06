package br.ufsc.framework.services.security.cas;

import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.util.Assert;

public class CustomUserDetailsCASAuthenticationProvider extends CasAuthenticationProvider {

    private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    private ServiceProperties serviceProperties;

    private CASUserDetailsSelector userDetailsSelector;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(getTicketValidator(), "A ticketValidator must be set");
        Assert.notNull(getStatelessTicketCache(), "A statelessTicketCache must be set");
        Assert.hasText(getKey(), "A Key is required so CasAuthenticationProvider can identify tokens it previously authenticated");
        Assert.notNull(this.messages, "A message source must be set");
        Assert.notNull(this.serviceProperties, "serviceProperties is a required field.");
        Assert.notNull(this.userDetailsSelector, "A userDetailsSelector must be set");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        if (authentication instanceof UsernamePasswordAuthenticationToken
            && (!CasAuthenticationFilter.CAS_STATEFUL_IDENTIFIER.equals(authentication.getPrincipal().toString()) && !CasAuthenticationFilter.CAS_STATELESS_IDENTIFIER
            .equals(authentication.getPrincipal().toString()))) {
            // UsernamePasswordAuthenticationToken not CAS related
            return null;
        }

        // If an existing CasAuthenticationToken, just check we created it
        if (authentication instanceof CasAuthenticationToken) {
            if (getKey().hashCode() == ((CasAuthenticationToken) authentication).getKeyHash()) {
                return authentication;
            } else {
                throw new BadCredentialsException(messages.getMessage("CasAuthenticationProvider.incorrectKey",
                    "The presented CasAuthenticationToken does not contain the expected key"));
            }
        }

        // Ensure credentials are presented
        if ((authentication.getCredentials() == null) || "".equals(authentication.getCredentials())) {
            throw new BadCredentialsException(messages.getMessage("CasAuthenticationProvider.noServiceTicket",
                "Failed to provide a CAS service ticket to validate"));
        }

        boolean stateless = false;

        if (authentication instanceof UsernamePasswordAuthenticationToken
            && CasAuthenticationFilter.CAS_STATELESS_IDENTIFIER.equals(authentication.getPrincipal())) {
            stateless = true;
        }

        CasAuthenticationToken result = null;

        if (stateless) {
            // Try to obtain from cache
            result = getStatelessTicketCache().getByTicketId(authentication.getCredentials().toString());
        }

        if (result == null) {
            result = this.authenticateNow(authentication);
            result.setDetails(authentication.getDetails());
        }

        if (stateless) {
            // Add to cache
            getStatelessTicketCache().putTicketInCache(result);
        }

        return result;
    }

    // Aqui nos delegamos para o userDetailsSelector escolher como pegar o usuario (pode ser de diferentes bases de dados etc.)
    private CasAuthenticationToken authenticateNow(Authentication authentication) throws AuthenticationException {
        try {
            final Assertion assertion = getTicketValidator().validate(authentication.getCredentials().toString(),
                serviceProperties.getService());

            final UserDetails userDetails = userDetailsSelector.getUserDetailsByCASAttributePrincipal(assertion.getPrincipal());
            if (userDetails == null)
                throw new BadCredentialsException("UserDetails is NULL");
            userDetailsChecker.check(userDetails);
            return new CasAuthenticationToken(getKey(), userDetails, authentication.getCredentials(), userDetails.getAuthorities(),
                userDetails, assertion);
        } catch (final TicketValidationException e) {
            e.printStackTrace();
            throw new BadCredentialsException("", e);
        }
    }

    public void setUserDetailsSelector(CASUserDetailsSelector userDetailsSelector) {
        this.userDetailsSelector = userDetailsSelector;
    }

    @Override
    public void setServiceProperties(final ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }
}
