package com.upn.capacitaciones.api.capacitacion.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "capacitacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Capacitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "capacitacion_id")
    private Long capacitacionId;

    @Column(name = "codigo", length = 10, nullable = false)
    private String codigo;

    @Column(name = "titulo", length = 200, nullable = false)
    private String titulo;

    @Column(name = "tema_prompt", columnDefinition = "TEXT", nullable = false)
    private String temaPrompt;

    @Column(name = "nivel", length = 20, nullable = false)
    private String nivel;

    @Column(name = "contenido_resumen", columnDefinition = "TEXT")
    private String contenidoResumen;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "nota_minima")
    private Double notaMinima;

    @Column(name = "activo")
    private Boolean activo;

    // RELACIÓN: Muchas capacitaciones pueden ser creadas por 1 Perfil (Creador)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creador_id")
    private Perfil creador;

    // RELACIÓN INVERSA: 1 Capacitación tiene muchas Preguntas
    @OneToMany(mappedBy = "capacitacion", cascade = CascadeType.ALL)
    private List<Pregunta> preguntas;
}