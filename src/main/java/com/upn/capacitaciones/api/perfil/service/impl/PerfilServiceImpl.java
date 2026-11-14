package com.upn.capacitaciones.api.perfil.service.impl;

import com.upn.capacitaciones.api.common.exception.DuplicateResourceException;
import com.upn.capacitaciones.api.common.exception.ResourceNotFoundException;
import com.upn.capacitaciones.api.common.security.PasswordPolicy;
import com.upn.capacitaciones.api.perfil.dto.PerfilRequest;
import com.upn.capacitaciones.api.perfil.dto.PerfilResponse;
import com.upn.capacitaciones.api.perfil.entity.Perfil;
import com.upn.capacitaciones.api.perfil.repository.PerfilRepository;
import com.upn.capacitaciones.api.perfil.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements PerfilService {

    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordPolicy passwordPolicy;

    @Override
    @Transactional(readOnly = true)
    public List<PerfilResponse> listar() {
        return perfilRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PerfilResponse obtenerPorId(Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado con ID: " + id));
        return toResponse(perfil);
    }

    @Override
    @Transactional
    public PerfilResponse crear(PerfilRequest request) {
        if (perfilRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario registrado con el correo: " + request.getEmail());
        }

        passwordPolicy.validar(request.getPassword());

        Perfil perfil = Perfil.builder()
                .nombreCompleto(request.getNombreCompleto())
                .email(request.getEmail().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(request.getRol().toUpperCase())
                .activo(request.getActivo() != null ? request.getActivo() : true)
                .build();

        return toResponse(perfilRepository.save(perfil));
    }

    @Override
    @Transactional
    public PerfilResponse actualizar(Long id, PerfilRequest request) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado con ID: " + id));

        passwordPolicy.validar(request.getPassword());

        perfil.setNombreCompleto(request.getNombreCompleto());
        perfil.setEmail(request.getEmail().toLowerCase());
        perfil.setPassword(passwordEncoder.encode(request.getPassword()));
        perfil.setRol(request.getRol().toUpperCase());
        if (request.getActivo() != null) {
            perfil.setActivo(request.getActivo());
        }

        return toResponse(perfilRepository.save(perfil));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado con ID: " + id));
        perfil.setActivo(false);
        perfilRepository.save(perfil);
    }

    private PerfilResponse toResponse(Perfil p) {
        return PerfilResponse.builder()
                .perfilId(p.getPerfilId())
                .nombreCompleto(p.getNombreCompleto())
                .email(p.getEmail())
                .rol(p.getRol())
                .activo(p.getActivo())
                .build();
    }
}