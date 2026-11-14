package com.upn.capacitaciones.api.pregunta.service;

import com.upn.capacitaciones.api.pregunta.dto.PreguntaRequest;
import com.upn.capacitaciones.api.pregunta.dto.PreguntaResponse;

import java.util.List;

public interface PreguntaService {
    List<PreguntaResponse> listarPorCapacitacion(Long capacitacionId);
    PreguntaResponse crear(PreguntaRequest request);
    PreguntaResponse actualizar(Long id, PreguntaRequest request);
    void eliminar(Long id);
}