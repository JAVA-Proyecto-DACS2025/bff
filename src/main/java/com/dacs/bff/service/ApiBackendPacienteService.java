package com.dacs.bff.service;

import java.util.List;

import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.dto.PacienteDto.ApiHospitalResponse;

public interface ApiBackendPacienteService {

    public List<PacienteDto.FrontResponse> getPacientesByIds(List<Long> ids);

    public PacienteDto.FrontResponse savePaciente(PacienteDto paciente) throws Exception;

    public PacienteDto.FrontResponse updatePaciente(PacienteDto paciente) throws Exception;

    public PacienteDto.FrontResponse deletePaciente(Long id) throws Exception;

    public List<PacienteDto.FrontResponse> getPacientesHospital(Integer cantidad);
}
