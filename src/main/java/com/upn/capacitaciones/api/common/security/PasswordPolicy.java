package com.upn.capacitaciones.api.common.security;

import org.springframework.stereotype.Component;

@Component
public class PasswordPolicy {

    public void validar(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
        boolean tieneMayuscula = password.chars().anyMatch(Character::isUpperCase);
        boolean tieneMinuscula = password.chars().anyMatch(Character::isLowerCase);
        boolean tieneNumero = password.chars().anyMatch(Character::isDigit);
        boolean tieneEspecial = password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));

        if (!tieneMayuscula || !tieneMinuscula || !tieneNumero || !tieneEspecial) {
            throw new IllegalArgumentException(
                    "La contraseña debe incluir mayúscula, minúscula, número y carácter especial"
            );
        }
    }
}