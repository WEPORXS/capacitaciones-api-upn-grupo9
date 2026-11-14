package com.upn.capacitaciones.api.evaluacion.service;

import com.upn.capacitaciones.api.evaluacion.dto.EvaluacionRequest;
import com.upn.capacitaciones.api.evaluacion.dto.EvaluacionResponse;

import java.util.List;

public interface EvaluacionService {
    EvaluacionResponse rendirEvaluacion(EvaluacionRequest request);
    List<EvaluacionResponse> listarEvaluaciones();
    List<EvaluacionResponse> listarPorCapacitacion(Long capacitacionId);
}