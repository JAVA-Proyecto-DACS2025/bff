package com.dacs.bff.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDto {

    private Long id;
    private String nombre;
    private String dni;
    private String edad;
    private String altura;
    private String peso;
    private String direccion;
    private String telefono;
}
