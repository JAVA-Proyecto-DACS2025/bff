package com.dacs.bff.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MiembroEquipoDTO {

    // Datos del personal medico enviados a front
    private Long personalId;
    private String legajo;
    private String nombre;
    private Long cirugiaId;
    private String rol;


    @Data
    static public class Create {
        @JsonProperty("cirugiaId")
        private Long cirugiaId;

        @JsonProperty("personalId")
        private Long personalId;

        @JsonProperty("rol")
        private String rol;
    }

    @Data
    static public class BackResponse {
        private Long cirugiaId;
        private String rol;
        private java.time.LocalDateTime fechaAsignacion;

        private PersonalResponseDto personal;
    }
}
