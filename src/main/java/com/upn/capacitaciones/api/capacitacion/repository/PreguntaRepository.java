package com.upn.capacitaciones.api.capacitacion.repository;

import com.upn.capacitaciones.api.capacitacion.entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
}