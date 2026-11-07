package com.upn.capacitaciones.api.capacitacion.service.impl;

import com.upn.capacitaciones.api.capacitacion.dto.CapacitacionRequest;
import com.upn.capacitaciones.api.capacitacion.dto.CapacitacionResponse;
import com.upn.capacitaciones.api.capacitacion.entity.Capacitacion;
import com.upn.capacitaciones.api.capacitacion.repository.CapacitacionRepository;
import com.upn.capacitaciones.api.capacitacion.service.CapacitacionService;
import com.upn.capacitaciones.api.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CapacitacionServiceImpl implements CapacitacionService {

    private final CapacitacionRepository capacitacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CapacitacionResponse> listar() {
        return capacitacionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CapacitacionResponse obtenerPorId(Long id) {
        return toResponse(buscarEntidad(id));
    }

    @Override
    @Transactional
    public CapacitacionResponse crear(CapacitacionRequest request) {
        Capacitacion entidad = Capacitacion.builder()
                .codigo(request.getCodigo().trim())
                .titulo(request.getTitulo().trim())
                .temaPrompt(request.getTemaPrompt().trim())
                .nivel(request.getNivel().trim().toUpperCase())
                .estado(request.getEstado() != null ? request.getEstado().trim().toUpperCase() : "BORRADOR")
                .notaMinima(request.getNotaMinima())
                .activo(request.getActivo())
                .build();

        return toResponse(capacitacionRepository.save(entidad));
    }

    @Override
    @Transactional
    public CapacitacionResponse actualizar(Long id, CapacitacionRequest request) {
        Capacitacion entidad = buscarEntidad(id);
        entidad.setCodigo(request.getCodigo().trim());
        entidad.setTitulo(request.getTitulo().trim());
        entidad.setTemaPrompt(request.getTemaPrompt().trim());
        entidad.setNivel(request.getNivel().trim().toUpperCase());
        if (request.getEstado() != null) {
            entidad.setEstado(request.getEstado().trim().toUpperCase());
        }
        entidad.setNotaMinima(request.getNotaMinima());
        entidad.setActivo(request.getActivo());

        return toResponse(capacitacionRepository.save(entidad));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Capacitacion entidad = buscarEntidad(id);
        entidad.setActivo(false);
        capacitacionRepository.save(entidad);
    }

    private Capacitacion buscarEntidad(Long id) {
        return capacitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la capacitación con id " + id));
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