package com.upn.capacitaciones.api.pregunta.repository;

import com.upn.capacitaciones.api.pregunta.entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {

    List<Pregunta> findByCapacitacion_CapacitacionIdAndActivoTrue(Long capacitacionId);
}