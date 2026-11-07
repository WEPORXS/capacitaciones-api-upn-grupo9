package com.upn.capacitaciones.api.capacitacion.repository;

import com.upn.capacitaciones.api.capacitacion.entity.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
}