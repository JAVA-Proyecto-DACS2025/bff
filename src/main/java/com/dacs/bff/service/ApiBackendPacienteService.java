package com.dacs.bff.service;

import java.util.List;

import com.dacs.bff.dto.PacienteDto;

public interface ApiBackendPacienteService {

    public List<PacienteDto> getPacientes();
    public List<PacienteDto> getPacientes(String search);

    public PacienteDto savePaciente(PacienteDto paciente) throws Exception;

    public PacienteDto updatePaciente(PacienteDto paciente) throws Exception;

    public PacienteDto deletePaciente(Long id) throws Exception;
}
