package com.upn.capacitaciones.api.evaluacion.controller;

import com.upn.capacitaciones.api.evaluacion.dto.EvaluacionRequest;
import com.upn.capacitaciones.api.evaluacion.dto.EvaluacionResponse;
import com.upn.capacitaciones.api.evaluacion.service.EvaluacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ALUMNO', 'ADMIN', 'CREADOR')")
    public ResponseEntity<EvaluacionResponse> rendirEvaluacion(@Valid @RequestBody EvaluacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionService.rendirEvaluacion(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ALUMNO', 'ADMIN', 'CREADOR')")
    public ResponseEntity<List<EvaluacionResponse>> listarEvaluaciones() {
        return ResponseEntity.ok(evaluacionService.listarEvaluaciones());
    }

    @GetMapping("/capacitacion/{capacitacionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CREADOR')")
    public ResponseEntity<List<EvaluacionResponse>> listarPorCapacitacion(@PathVariable Long capacitacionId) {
        return ResponseEntity.ok(evaluacionService.listarPorCapacitacion(capacitacionId));
    }
}