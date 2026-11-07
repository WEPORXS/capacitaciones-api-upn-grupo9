package com.upn.capacitaciones.api.capacitacion.repository;

import com.upn.capacitaciones.api.capacitacion.entity.Capacitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapacitacionRepository extends JpaRepository<Capacitacion, Long> {
}