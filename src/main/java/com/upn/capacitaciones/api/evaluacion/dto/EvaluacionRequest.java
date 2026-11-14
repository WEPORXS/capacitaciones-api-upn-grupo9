package com.upn.capacitaciones.api.evaluacion.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EvaluacionRequest {

    @NotNull(message = "El ID de la capacitación es obligatorio")
    private Long capacitacionId;

    @NotEmpty(message = "Debe enviar las respuestas del examen")
    private Map<Long, String> respuestas;
}