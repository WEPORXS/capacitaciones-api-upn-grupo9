package com.upn.capacitaciones.api.capacitacion.repository;

import com.upn.capacitaciones.api.capacitacion.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}