package com.upn.capacitaciones.api.capacitacion.controller;

import com.upn.capacitaciones.api.capacitacion.dto.CapacitacionRequest;
import com.upn.capacitaciones.api.capacitacion.dto.CapacitacionResponse;
import com.upn.capacitaciones.api.capacitacion.service.CapacitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/capacitaciones")
@RequiredArgsConstructor
public class CapacitacionController {

    private final CapacitacionService capacitacionService;

    @GetMapping
    public ResponseEntity<List<CapacitacionResponse>> listar() {
        return ResponseEntity.ok(capacitacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapacitacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(capacitacionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CapacitacionResponse> crear(@Valid @RequestBody CapacitacionRequest request) {
        CapacitacionResponse creada = capacitacionService.crear(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getCapacitacionId())
                .toUri();

        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapacitacionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CapacitacionRequest request) {
        return ResponseEntity.ok(capacitacionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        capacitacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}