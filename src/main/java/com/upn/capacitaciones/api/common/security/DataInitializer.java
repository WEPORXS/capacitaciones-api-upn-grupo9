package com.upn.capacitaciones.api.common.security;

import com.upn.capacitaciones.api.perfil.entity.Perfil;
import com.upn.capacitaciones.api.perfil.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        asegurarUsuario("Administrador UPN", "admin@upn.edu.pe", "Upn2026*", RolesSistema.ADMIN);
        asegurarUsuario("Docente Creador UPN", "creador@upn.edu.pe", "Upn2026*", RolesSistema.CREADOR);
        asegurarUsuario("Alumno Colaborador UPN", "alumno@upn.edu.pe", "Upn2026*", RolesSistema.ALUMNO);
    }

    private void asegurarUsuario(String nombre, String email, String rawPassword, String rol) {
        Perfil perfil = perfilRepository.findByEmailIgnoreCase(email)
                .orElseGet(() -> Perfil.builder()
                        .nombreCompleto(nombre)
                        .email(email)
                        .rol(rol)
                        .activo(true)
                        .build());

        perfil.setPassword(passwordEncoder.encode(rawPassword));
        perfil.setRol(rol);
        perfil.setActivo(true);
        perfilRepository.save(perfil);
    }
}
