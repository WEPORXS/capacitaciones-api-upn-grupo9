package com.upn.capacitaciones.api.perfil.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilRequest {

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotBlank(message = "El rol es obligatorio (ADMIN, CREADOR o ALUMNO)")
    @Pattern(regexp = "^(ADMIN|CREADOR|ALUMNO)$", message = "El rol debe ser ADMIN, CREADOR o ALUMNO")
    private String rol;

    private Boolean activo;
}
