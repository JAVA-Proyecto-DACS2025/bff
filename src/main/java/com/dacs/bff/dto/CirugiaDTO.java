package com.dacs.bff.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class CirugiaDTO {

    @Data
    public static class BackResponse {
        private Long id;
        private String servicio;
        private String prioridad;
        private String fecha_hora_inicio;
        private String estado;
        private String anestesia;
        private String tipo;
        private PacienteDto.BackResponse paciente;    
        private QuirofanoDto quirofano;
    }

    @Data
    public static class FrontResponse {
        private Long id;
        private Long pacienteId;  
        private String paciente;
        private String dni;
        private String servicio;
        private String prioridad;
        private String fechaInicio;
        private String horaInicio;
        private String estado;
        private String anestesia;
        private String tipo;
          
        private String quirofano;
        private Long quirofanoId;
    }

    @Data
    public static class Create {
        private String servicio;
        private String prioridad;
        private String fecha_hora_inicio;
        private String estado;
        private String anestesia;
        private String tipo;
        private Long paciente;    
        private Long quirofano;
    }

    @Data
    public static class Update extends Create {
        private Long id;
    }
}


