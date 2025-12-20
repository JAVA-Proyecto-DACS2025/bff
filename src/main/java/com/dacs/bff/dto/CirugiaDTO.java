package com.dacs.bff.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CirugiaDTO {

    @Data
    public static class BackResponse {
        private Long id;
        private String prioridad;
        private LocalDateTime fecha_hora_inicio;
        private String estado;
        private String anestesia;
        private String tipo;
        private PacienteDto.BackResponse paciente;    
        private QuirofanoDto quirofano;
        private ServicioDto servicio;
    }

    @Data
    public static class FrontResponse {
        private Long id;
        private Long pacienteId;  
        private String pacienteNombre;
        private String dni;
        private String servicioNombre;
        private Long servicioId;
        private String prioridad;
        private String fechaInicio;
        private String horaInicio;
        private String estado;
        private String anestesia;
        private String tipo;
        private String quirofanoNombre;
        private Long quirofanoId;
    }
}


