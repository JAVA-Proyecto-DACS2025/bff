package com.dacs.bff.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalRequestDto {
    private Long id;
    private String legajo;
    private String nombre;
    private String especialidad;
    private String rol;
    private String estado;   //???
    private String telefono;
}
