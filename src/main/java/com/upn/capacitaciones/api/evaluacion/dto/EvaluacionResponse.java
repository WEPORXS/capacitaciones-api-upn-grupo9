package com.upn.capacitaciones.api.evaluacion.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EvaluacionResponse {

    private Long evaluacionId;
    private Long capacitacionId;
    private String tituloCapacitacion;
    private Long alumnoId;
    private String nombreAlumno;
    private Integer totalPreguntas;
    private Integer aciertos;
    private Double puntaje;
    private Double notaMinimaRequerida;
    private Boolean aprobado;
    private LocalDateTime fechaEvaluacion;
}