package com.upn.capacitaciones.api.pregunta.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PreguntaResponse {

    private Long preguntaId;
    private Long capacitacionId;
    private String enunciado;
    private String opcionA;
    private String opcionB;
    private String opcionC;
    private String opcionD;
    private String justificacion;
    private Boolean activo;
}