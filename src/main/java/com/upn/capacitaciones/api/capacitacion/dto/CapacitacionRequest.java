package com.upn.capacitaciones.api.capacitacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CapacitacionRequest {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 10, message = "El código no debe superar los 10 caracteres")
    private String codigo;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no debe superar los 200 caracteres")
    private String titulo;

    @NotBlank(message = "El tema o prompt para la IA es obligatorio")
    private String temaPrompt;

    @NotBlank(message = "El nivel es obligatorio (BASICO, INTERMEDIO, AVANZADO)")
    @Size(max = 20, message = "El nivel no debe superar los 20 caracteres")
    private String nivel;

    private String estado;

    @NotNull(message = "La nota mínima es obligatoria")
    private Double notaMinima;

    @NotNull(message = "El campo activo es obligatorio")
    private Boolean activo;
}