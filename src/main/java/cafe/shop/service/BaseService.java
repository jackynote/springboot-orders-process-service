package cafe.shop.service;

import cafe.shop.model.AuthUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseService {

    protected boolean isLogin() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    protected AuthUser currentUser() {
        try {
            return isLogin() ? (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal() : null;
        } catch (Exception e) {

        }
        return null;
    }
}
