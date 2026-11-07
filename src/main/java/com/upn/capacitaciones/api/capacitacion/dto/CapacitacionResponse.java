package com.upn.capacitaciones.api.capacitacion.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CapacitacionResponse {
    private Long capacitacionId;
    private String codigo;
    private String titulo;
    private String temaPrompt;
    private String nivel;
    private String estado;
    private Double notaMinima;
    private Boolean activo;
}