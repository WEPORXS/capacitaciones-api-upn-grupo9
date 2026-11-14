package com.upn.capacitaciones.api.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String token;
    private String type;
    private Long perfilId;
    private String nombreCompleto;
    private String email;
    private String rol;
}