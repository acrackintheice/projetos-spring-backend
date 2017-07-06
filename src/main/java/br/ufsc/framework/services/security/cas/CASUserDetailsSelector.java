package br.ufsc.framework.services.security.cas;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.security.core.userdetails.UserDetails;

public interface CASUserDetailsSelector {

    public UserDetails getUserDetailsByCASAttributePrincipal(AttributePrincipal principal);

}
