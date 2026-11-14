package com.upn.capacitaciones.api.perfil.controller;

import com.upn.capacitaciones.api.perfil.dto.PerfilRequest;
import com.upn.capacitaciones.api.perfil.dto.PerfilResponse;
import com.upn.capacitaciones.api.perfil.service.PerfilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/perfiles")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public ResponseEntity<List<PerfilResponse>> listar() {
        return ResponseEntity.ok(perfilService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(perfilService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PerfilResponse> crear(@Valid @RequestBody PerfilRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PerfilRequest request) {
        return ResponseEntity.ok(perfilService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        perfilService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
