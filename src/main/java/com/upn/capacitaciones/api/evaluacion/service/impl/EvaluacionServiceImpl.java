package com.upn.capacitaciones.api.evaluacion.service.impl;

import com.upn.capacitaciones.api.capacitacion.entity.Capacitacion;
import com.upn.capacitaciones.api.perfil.entity.Perfil;
import com.upn.capacitaciones.api.capacitacion.repository.CapacitacionRepository;
import com.upn.capacitaciones.api.perfil.repository.PerfilRepository;
import com.upn.capacitaciones.api.common.exception.ResourceNotFoundException;
import com.upn.capacitaciones.api.common.security.CurrentUserContext;
import com.upn.capacitaciones.api.common.security.RolesSistema;
import com.upn.capacitaciones.api.evaluacion.dto.EvaluacionRequest;
import com.upn.capacitaciones.api.evaluacion.dto.EvaluacionResponse;
import com.upn.capacitaciones.api.evaluacion.entity.Evaluacion;
import com.upn.capacitaciones.api.evaluacion.repository.EvaluacionRepository;
import com.upn.capacitaciones.api.evaluacion.service.EvaluacionService;
import com.upn.capacitaciones.api.pregunta.entity.Pregunta;
import com.upn.capacitaciones.api.pregunta.repository.PreguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluacionServiceImpl implements EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final CapacitacionRepository capacitacionRepository;
    private final PreguntaRepository preguntaRepository;
    private final PerfilRepository perfilRepository;
    private final CurrentUserContext currentUserContext;

    @Override
    @Transactional
    public EvaluacionResponse rendirEvaluacion(EvaluacionRequest request) {
        Capacitacion capacitacion = capacitacionRepository.findById(request.getCapacitacionId())
                .orElseThrow(() -> new ResourceNotFoundException("Capacitación no encontrada con ID: " + request.getCapacitacionId()));

        Perfil alumno = perfilRepository.findById(currentUserContext.getPerfilId())
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de alumno no encontrado"));

        List<Pregunta> preguntas = preguntaRepository.findByCapacitacion_CapacitacionIdAndActivoTrue(capacitacion.getCapacitacionId());
        if (preguntas.isEmpty()) {
            throw new ResourceNotFoundException("La capacitación aún no tiene preguntas activas registradas");
        }

        int aciertos = 0;
        for (Pregunta p : preguntas) {
            String respuestaMarcada = request.getRespuestas().get(p.getPreguntaId());
            if (respuestaMarcada != null && p.getRespuestaCorrecta().equalsIgnoreCase(respuestaMarcada.trim())) {
                aciertos++;
            }
        }

        double puntaje = Math.round(((aciertos * 20.0) / preguntas.size()) * 100.0) / 100.0;
        double notaMinima = capacitacion.getNotaMinima() != null ? capacitacion.getNotaMinima() : 11.0;
        boolean aprobado = puntaje >= notaMinima;

        Evaluacion evaluacion = Evaluacion.builder()
                .capacitacion(capacitacion)
                .alumno(alumno)
                .puntaje(puntaje)
                .aprobado(aprobado)
                .fechaEvaluacion(LocalDateTime.now())
                .build();

        Evaluacion guardada = evaluacionRepository.save(evaluacion);
        return toResponse(guardada, preguntas.size(), aciertos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluacionResponse> listarEvaluaciones() {
        // Regla de negocio (Paso 20 del docente): El ALUMNO solo puede ver sus propias evaluaciones
        if (RolesSistema.ALUMNO.equals(currentUserContext.getRol())) {
            return evaluacionRepository.findByAlumno_PerfilIdOrderByFechaEvaluacionDesc(currentUserContext.getPerfilId())
                    .stream()
                    .map(e -> toResponse(e, null, null))
                    .toList();
        }
        return evaluacionRepository.findAll()
                .stream()
                .map(e -> toResponse(e, null, null))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluacionResponse> listarPorCapacitacion(Long capacitacionId) {
        return evaluacionRepository.findByCapacitacion_CapacitacionIdOrderByFechaEvaluacionDesc(capacitacionId)
                .stream()
                .map(e -> toResponse(e, null, null))
                .toList();
    }

    private EvaluacionResponse toResponse(Evaluacion e, Integer totalPreguntas, Integer aciertos) {
        return EvaluacionResponse.builder()
                .evaluacionId(e.getEvaluacionId())
                .capacitacionId(e.getCapacitacion().getCapacitacionId())
                .tituloCapacitacion(e.getCapacitacion().getTitulo())
                .alumnoId(e.getAlumno().getPerfilId())
                .nombreAlumno(e.getAlumno().getNombreCompleto())
                .totalPreguntas(totalPreguntas)
                .aciertos(aciertos)
                .puntaje(e.getPuntaje())
                .notaMinimaRequerida(e.getCapacitacion().getNotaMinima())
                .aprobado(e.getAprobado())
                .fechaEvaluacion(e.getFechaEvaluacion())
                .build();
    }
}
