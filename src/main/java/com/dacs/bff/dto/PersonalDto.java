package com.dacs.bff.dto;

import lombok.Data;

@Data
public class PersonalDto {
    private Long id;
    private String legajo;
    private String nombre;
    private String dni;
    private String especialidad;
    private String rol;
    private String estado;  
    private String telefono;

    @Data
    static public class Lite
    {
        private Long id;
        private String dni;
        private String legajo;
        private String nombre;
        private String rol;
    }
}
