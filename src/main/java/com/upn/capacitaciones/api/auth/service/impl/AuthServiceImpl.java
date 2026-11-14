package com.upn.capacitaciones.api.auth.service.impl;

import com.upn.capacitaciones.api.auth.dto.LoginRequest;
import com.upn.capacitaciones.api.auth.dto.LoginResponse;
import com.upn.capacitaciones.api.auth.service.AuthService;
import com.upn.capacitaciones.api.perfil.entity.Perfil;
import com.upn.capacitaciones.api.perfil.repository.PerfilRepository;
import com.upn.capacitaciones.api.common.exception.UnauthorizedException;
import com.upn.capacitaciones.api.common.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Perfil perfil = perfilRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Credenciales inválidas"));

        if (!Boolean.TRUE.equals(perfil.getActivo())) {
            throw new UnauthorizedException("El usuario se encuentra inactivo");
        }

        if (!passwordEncoder.matches(request.getPassword(), perfil.getPassword())) {
            throw new UnauthorizedException("Credenciales inválidas");
        }

        String token = jwtTokenService.generar(perfil);

        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .perfilId(perfil.getPerfilId())
                .nombreCompleto(perfil.getNombreCompleto())
                .email(perfil.getEmail())
                .rol(perfil.getRol())
                .build();
    }
}