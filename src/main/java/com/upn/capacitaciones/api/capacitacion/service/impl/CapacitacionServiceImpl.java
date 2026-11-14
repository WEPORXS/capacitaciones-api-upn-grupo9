package com.upn.capacitaciones.api.capacitacion.service.impl;

import com.upn.capacitaciones.api.capacitacion.dto.CapacitacionRequest;
import com.upn.capacitaciones.api.capacitacion.dto.CapacitacionResponse;
import com.upn.capacitaciones.api.capacitacion.entity.Capacitacion;
import com.upn.capacitaciones.api.perfil.entity.Perfil;
import com.upn.capacitaciones.api.capacitacion.repository.CapacitacionRepository;
import com.upn.capacitaciones.api.perfil.repository.PerfilRepository;
import com.upn.capacitaciones.api.capacitacion.service.CapacitacionService;
import com.upn.capacitaciones.api.common.exception.DuplicateResourceException;
import com.upn.capacitaciones.api.common.exception.ResourceNotFoundException;
import com.upn.capacitaciones.api.common.security.CurrentUserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CapacitacionServiceImpl implements CapacitacionService {

    private final CapacitacionRepository capacitacionRepository;
    private final PerfilRepository perfilRepository;
    private final CurrentUserContext currentUserContext;

    @Override
    @Transactional(readOnly = true)
    public List<CapacitacionResponse> listar() {
        return capacitacionRepository.findAll().stream()
                .filter(c -> Boolean.TRUE.equals(c.getActivo()))
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CapacitacionResponse obtenerPorId(Long id) {
        Capacitacion cap = capacitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Capacitación no encontrada con ID: " + id));
        return toResponse(cap);
    }

    @Override
    @Transactional
    public CapacitacionResponse crear(CapacitacionRequest request) {
        if (capacitacionRepository.existsByCodigoIgnoreCase(request.getCodigo())) {
            throw new DuplicateResourceException("Ya existe una capacitación con el código: " + request.getCodigo());
        }

        Perfil creador = perfilRepository.findById(currentUserContext.getPerfilId())
                .orElseThrow(() -> new ResourceNotFoundException("Perfil creador no encontrado"));

        Capacitacion entidad = Capacitacion.builder()
                .codigo(request.getCodigo().toUpperCase())
                .titulo(request.getTitulo())
                .temaPrompt(request.getTemaPrompt())
                .nivel(request.getNivel())
                .estado(request.getEstado() != null ? request.getEstado() : "BORRADOR")
                .notaMinima(request.getNotaMinima())
                .activo(request.getActivo() != null ? request.getActivo() : true)
                .creador(creador)
                .build();
        return toResponse(capacitacionRepository.save(entidad));
    }

    @Override
    @Transactional
    public CapacitacionResponse actualizar(Long id, CapacitacionRequest request) {
        Capacitacion cap = capacitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Capacitación no encontrada con ID: " + id));
        cap.setCodigo(request.getCodigo().toUpperCase());
        cap.setTitulo(request.getTitulo());
        cap.setTemaPrompt(request.getTemaPrompt());
        cap.setNivel(request.getNivel());
        cap.setEstado(request.getEstado());
        cap.setNotaMinima(request.getNotaMinima());
        cap.setActivo(request.getActivo());
        return toResponse(capacitacionRepository.save(cap));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Capacitacion cap = capacitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Capacitación no encontrada con ID: " + id));
        cap.setActivo(false); // Baja lógica preservando integridad referencial
        capacitacionRepository.save(cap);
    }

    private CapacitacionResponse toResponse(Capacitacion c) {
        return CapacitacionResponse.builder()
                .capacitacionId(c.getCapacitacionId())
                .codigo(c.getCodigo())
                .titulo(c.getTitulo())
                .temaPrompt(c.getTemaPrompt())
                .nivel(c.getNivel())
                .estado(c.getEstado())
                .notaMinima(c.getNotaMinima())
                .activo(c.getActivo())
                .build();
    }
}
