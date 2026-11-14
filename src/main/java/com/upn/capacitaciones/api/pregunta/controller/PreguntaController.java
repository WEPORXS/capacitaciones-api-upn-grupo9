package com.upn.capacitaciones.api.pregunta.controller;

import com.upn.capacitaciones.api.pregunta.dto.PreguntaRequest;
import com.upn.capacitaciones.api.pregunta.dto.PreguntaResponse;
import com.upn.capacitaciones.api.pregunta.service.PreguntaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/preguntas")
@RequiredArgsConstructor
public class PreguntaController {

    private final PreguntaService preguntaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CREADOR', 'ALUMNO')")
    public ResponseEntity<List<PreguntaResponse>> listarPorCapacitacion(@RequestParam Long capacitacionId) {
        return ResponseEntity.ok(preguntaService.listarPorCapacitacion(capacitacionId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CREADOR')")
    public ResponseEntity<PreguntaResponse> crear(@Valid @RequestBody PreguntaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(preguntaService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CREADOR')")
    public ResponseEntity<PreguntaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PreguntaRequest request) {
        return ResponseEntity.ok(preguntaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CREADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        preguntaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
