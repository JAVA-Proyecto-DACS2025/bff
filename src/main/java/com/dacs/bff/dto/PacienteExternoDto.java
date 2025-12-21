package com.dacs.bff.dto;

import java.util.List;

import lombok.Data;

@Data
public class PacienteExternoDto {
    private List<PacienteDto.ApiHospitalResponse> results;
}