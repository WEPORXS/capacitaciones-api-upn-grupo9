package com.upn.capacitaciones.api.capacitacion.service;

import com.upn.capacitaciones.api.capacitacion.dto.CapacitacionRequest;
import com.upn.capacitaciones.api.capacitacion.dto.CapacitacionResponse;

import java.util.List;

public interface CapacitacionService {
    List<CapacitacionResponse> listar();
    CapacitacionResponse obtenerPorId(Long id);
    CapacitacionResponse crear(CapacitacionRequest request);
    CapacitacionResponse actualizar(Long id, CapacitacionRequest request);
    void eliminar(Long id);
}