package com.upn.capacitaciones.api.perfil.repository;

import com.upn.capacitaciones.api.perfil.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Optional<Perfil> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}