package com.dacs.bff.dto;

import lombok.Data;

@Data
public class CirugiaDTO {

    @Data
    public static class Response {
        private Long id;
        private String servicio;
        private String prioridad;
        private String fecha_hora_inicio;
        private String estado;
        private String anestesia;
        private String tipo;
        private PacienteDto paciente;    
        private QuirofanoDto quirofano;
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


