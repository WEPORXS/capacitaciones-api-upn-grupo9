package com.upn.capacitaciones.api.pregunta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreguntaRequest {

    @NotNull(message = "El ID de la capacitación es obligatorio")
    private Long capacitacionId;

    @NotBlank(message = "El enunciado de la pregunta es obligatorio")
    private String enunciado;

    @NotBlank(message = "La opción A es obligatoria")
    private String opcionA;

    @NotBlank(message = "La opción B es obligatoria")
    private String opcionB;

    @NotBlank(message = "La opción C es obligatoria")
    private String opcionC;

    @NotBlank(message = "La opción D es obligatoria")
    private String opcionD;

    @NotBlank(message = "La respuesta correcta es obligatoria (A, B, C o D)")
    @Pattern(regexp = "^[ABCDabcd]$", message = "La respuesta correcta debe ser A, B, C o D")
    private String respuestaCorrecta;

    private String justificacion;
}