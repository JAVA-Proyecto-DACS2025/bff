package com.dacs.bff.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDto {

    @Data
    public static class BackResponse {
        private Long id;
        private String nombre;
        private String apellido;
        private String dni;
        private Integer edad;
        private String fecha_nacimiento;
        private String direccion;
        private String telefono;
        private Double altura;
        private Double peso;
    }

    @Data
    public static class FrontResponse {
        private Long id;
        private String nombre;
        private String apellido;
        private String dni;
        private Integer edad;
        private String fecha_nacimiento;
        private String direccion;
        private String telefono;
        private Double altura;
        private Double peso;
    }

    @Data
    public static class FrontResponseLite {
        private String nombre;
        private String dni;
        private Long id;
    }

    @Data
    public static class ApiHospitalResponse {
        private Name name;
        private Location location;
        private String email;
        private Dob dob;
        private String phone;
        private String cell;
        private Id id;

    }

    @Data
    public static class Name {
        private String first;
        private String last;
    }

    @Data
    public static class Location {
        private String city;
        private String state;
    }

    @Data
    public static class Dob {
        private String date;
    }

    @Data
    public static class Id {
        private String value;
    }
}
