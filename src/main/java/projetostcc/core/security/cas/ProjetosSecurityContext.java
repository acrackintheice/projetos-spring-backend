package projetostcc.core.security.cas;

import br.ufsc.framework.services.security.CustomUser;
import projetostcc.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Nullable;

public class ProjetosSecurityContext {
    private ProjetosSecurityContext() {
    }

    @Nullable
    private static CustomUser getCurrentCustomUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUser) {
            return (CustomUser) principal;
        } else {
            return null;
        }

    }

    @Nullable
    public static Usuario getCurrentUser() {
        CustomUser currentCustomUser = getCurrentCustomUser();
        if(currentCustomUser != null ) {
            return currentCustomUser.getUsuarioTCC();
        } else {
            return null;
        }
    }

    public static boolean isCurrentUserAuthenticated() {
        return getCurrentUser() != null;
    }

    public static boolean currentUserHasRole(String role) {
        CustomUser user = getCurrentCustomUser();
        if (user == null) {
            return false;
        }
        for (GrantedAuthority ga : user.getAuthorities()) {
            if (ga.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }

}
