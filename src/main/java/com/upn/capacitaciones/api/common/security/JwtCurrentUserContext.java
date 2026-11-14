package com.upn.capacitaciones.api.common.security;

import com.upn.capacitaciones.api.common.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtCurrentUserContext implements CurrentUserContext {

    private Jwt getJwt() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            return jwt;
        }
        throw new UnauthorizedException("No hay un usuario autenticado con JWT en el contexto actual");
    }

    @Override
    public Long getPerfilId() {
        return Long.valueOf(getJwt().getSubject());
    }

    @Override
    public String getNombreCompleto() {
        return getJwt().getClaimAsString("unique_name");
    }

    @Override
    public String getEmail() {
        return getJwt().getClaimAsString("email");
    }

    @Override
    public String getRol() {
        return getJwt().getClaimAsString("role");
    }
}