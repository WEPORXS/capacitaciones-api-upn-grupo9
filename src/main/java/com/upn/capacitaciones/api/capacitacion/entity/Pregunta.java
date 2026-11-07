package com.upn.capacitaciones.api.capacitacion.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pregunta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pregunta_id")
    private Long preguntaId;

    // RELACIÓN PRINCIPAL: Muchas preguntas pertenecen a 1 Capacitación
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capacitacion_id", nullable = false)
    private Capacitacion capacitacion;

    @Column(name = "enunciado", columnDefinition = "TEXT", nullable = false)
    private String enunciado;

    @Column(name = "opcion_a", length = 255, nullable = false)
    private String opcionA;

    @Column(name = "opcion_b", length = 255, nullable = false)
    private String opcionB;

    @Column(name = "opcion_c", length = 255, nullable = false)
    private String opcionC;

    @Column(name = "opcion_d", length = 255, nullable = false)
    private String opcionD;

    @Column(name = "respuesta_correcta", length = 1, nullable = false)
    private String respuestaCorrecta;

    @Column(name = "justificacion", columnDefinition = "TEXT")
    private String justificacion;

    @Column(name = "activo")
    private Boolean activo;
}