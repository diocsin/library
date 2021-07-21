package com.example.library.library.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtil {

    private AuthUtil() {
    }

    /**
     * @return пользователь, аутентифицированный в текущем контексте безопасности или null.
     */
    public static UserDetails getUserFromContext() {
        return getUser(getContext().getAuthentication());
    }

    /**
     * @param authentication данные аутентификации пользователя
     * @return пользователь
     */
    public static UserDetails getUser(final Authentication authentication) {
        return authentication == null ? null : (UserDetails) authentication.getPrincipal();
    }

    /**
     * Обновление принципала пользователя. Необходимо при изменении учётных данных текущего
     * пользователя.
     *
     * @param user пользователь
     */
    public static void updatePrincipal(final UserDetails user) {
        Authentication token = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
                user.getAuthorities());
        getContext().setAuthentication(token);
    }

    /**
     * @return the current security context (never null)
     */
    private static SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }

}
