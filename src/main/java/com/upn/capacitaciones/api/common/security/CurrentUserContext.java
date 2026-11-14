package com.upn.capacitaciones.api.common.security;

public interface CurrentUserContext {
    Long getPerfilId();
    String getNombreCompleto();
    String getEmail();
    String getRol();
}