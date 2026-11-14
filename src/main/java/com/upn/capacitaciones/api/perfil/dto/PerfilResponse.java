package com.upn.capacitaciones.api.perfil.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PerfilResponse {

    private Long perfilId;
    private String nombreCompleto;
    private String email;
    private String rol;
    private Boolean activo;
}