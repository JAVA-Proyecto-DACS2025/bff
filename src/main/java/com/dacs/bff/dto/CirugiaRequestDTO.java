package com.dacs.bff.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CirugiaRequestDTO {
    private String servicio;
    private String prioridad;
    private LocalDateTime fecha_hora_inicio;
    private String estado;
    private String anestesia;
    private String tipo;
    private Long pacienteId;    
    private Long quirofanoId;
}

@Getter
@Setter
class CirugiaUpdateDTO extends CirugiaRequestDTO {
    private Long id;
}