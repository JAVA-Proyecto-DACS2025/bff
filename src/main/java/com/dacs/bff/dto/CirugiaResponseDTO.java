package com.dacs.bff.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CirugiaResponseDTO {
    private Long id;
    private String servicio;
    private String prioridad;
    private LocalDateTime fecha_hora_inicio;
    private String estado;
    private String anestesia;
    private String tipo;
    private PacienteDto paciente;
    private QuirofanoDto quirofano;
}

