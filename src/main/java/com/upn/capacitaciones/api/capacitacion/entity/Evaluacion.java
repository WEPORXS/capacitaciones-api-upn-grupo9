package com.upn.capacitaciones.api.capacitacion.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluacion_id")
    private Long evaluacionId;

    // RELACIÓN 1: Muchas evaluaciones pertenecen a 1 Capacitación
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capacitacion_id", nullable = false)
    private Capacitacion capacitacion;

    // RELACIÓN 2: Muchas evaluaciones son rendidas por 1 Alumno (Perfil)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Perfil alumno;

    @Column(name = "puntaje", nullable = false)
    private Double puntaje;

    @Column(name = "aprobado", nullable = false)
    private Boolean aprobado;

    @Column(name = "fecha_evaluacion")
    private LocalDateTime fechaEvaluacion;
}