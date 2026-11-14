package com.upn.capacitaciones.api.evaluacion.repository;

import com.upn.capacitaciones.api.evaluacion.entity.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

    List<Evaluacion> findByAlumno_PerfilIdOrderByFechaEvaluacionDesc(Long perfilId);

    List<Evaluacion> findByCapacitacion_CapacitacionIdOrderByFechaEvaluacionDesc(Long capacitacionId);
}