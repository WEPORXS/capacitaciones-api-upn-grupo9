package com.upn.capacitaciones.api.pregunta.service.impl;

import com.upn.capacitaciones.api.capacitacion.entity.Capacitacion;
import com.upn.capacitaciones.api.capacitacion.repository.CapacitacionRepository;
import com.upn.capacitaciones.api.common.exception.ResourceNotFoundException;
import com.upn.capacitaciones.api.pregunta.dto.PreguntaRequest;
import com.upn.capacitaciones.api.pregunta.dto.PreguntaResponse;
import com.upn.capacitaciones.api.pregunta.entity.Pregunta;
import com.upn.capacitaciones.api.pregunta.repository.PreguntaRepository;
import com.upn.capacitaciones.api.pregunta.service.PreguntaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreguntaServiceImpl implements PreguntaService {

    private final PreguntaRepository preguntaRepository;
    private final CapacitacionRepository capacitacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PreguntaResponse> listarPorCapacitacion(Long capacitacionId) {
        if (!capacitacionRepository.existsById(capacitacionId)) {
            throw new ResourceNotFoundException("Capacitación no encontrada con ID: " + capacitacionId);
        }
        return preguntaRepository.findByCapacitacion_CapacitacionIdAndActivoTrue(capacitacionId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PreguntaResponse crear(PreguntaRequest request) {
        Capacitacion capacitacion = capacitacionRepository.findById(request.getCapacitacionId())
                .orElseThrow(() -> new ResourceNotFoundException("Capacitación no encontrada con ID: " + request.getCapacitacionId()));

        Pregunta pregunta = Pregunta.builder()
                .capacitacion(capacitacion)
                .enunciado(request.getEnunciado())
                .opcionA(request.getOpcionA())
                .opcionB(request.getOpcionB())
                .opcionC(request.getOpcionC())
                .opcionD(request.getOpcionD())
                .respuestaCorrecta(request.getRespuestaCorrecta().toUpperCase())
                .justificacion(request.getJustificacion())
                .activo(true)
                .build();

        return toResponse(preguntaRepository.save(pregunta));
    }

    @Override
    @Transactional
    public PreguntaResponse actualizar(Long id, PreguntaRequest request) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pregunta no encontrada con ID: " + id));

        pregunta.setEnunciado(request.getEnunciado());
        pregunta.setOpcionA(request.getOpcionA());
        pregunta.setOpcionB(request.getOpcionB());
        pregunta.setOpcionC(request.getOpcionC());
        pregunta.setOpcionD(request.getOpcionD());
        pregunta.setRespuestaCorrecta(request.getRespuestaCorrecta().toUpperCase());
        pregunta.setJustificacion(request.getJustificacion());

        return toResponse(preguntaRepository.save(pregunta));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pregunta no encontrada con ID: " + id));
        pregunta.setActivo(false);
        preguntaRepository.save(pregunta);
    }

    private PreguntaResponse toResponse(Pregunta p) {
        return PreguntaResponse.builder()
                .preguntaId(p.getPreguntaId())
                .capacitacionId(p.getCapacitacion().getCapacitacionId())
                .enunciado(p.getEnunciado())
                .opcionA(p.getOpcionA())
                .opcionB(p.getOpcionB())
                .opcionC(p.getOpcionC())
                .opcionD(p.getOpcionD())
                .justificacion(p.getJustificacion())
                .activo(p.getActivo())
                .build();
    }
}
