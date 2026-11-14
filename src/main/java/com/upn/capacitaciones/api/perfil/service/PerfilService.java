package com.upn.capacitaciones.api.perfil.service;

import com.upn.capacitaciones.api.perfil.dto.PerfilRequest;
import com.upn.capacitaciones.api.perfil.dto.PerfilResponse;

import java.util.List;

public interface PerfilService {
    List<PerfilResponse> listar();
    PerfilResponse obtenerPorId(Long id);
    PerfilResponse crear(PerfilRequest request);
    PerfilResponse actualizar(Long id, PerfilRequest request);
    void eliminar(Long id);
}