package com.dacs.bff.dto;

import lombok.Data;

@Data
public class PersonalDto {

    @Data
    static public class FrontResponse {
        private Long id;
        private String legajo;
        private String nombre;
        private String dni;
        private String especialidad;
        private String rol;
        private String estado;
        private String telefono;
    }

    @Data
    static public class FrontResponseLite {
        private Long id;
        private String dni;
        private String legajo;
        private String nombre;
        private String rol;
    }

    @Data
    static public class BackResponse {
        private Long id;
        private String legajo;
        private String nombre;
        private String dni;
        private String especialidad;
        private String rol;
        private String estado;
        private String telefono;
    }

    @Data
    static public class Create {
        private String legajo;
        private String nombre;
        private String dni;
        private String especialidad;
        private String rol;
        private String estado;
        private String telefono;
    }

    @Data
    static public class Update extends Create {
        private Long id;
    }
}
