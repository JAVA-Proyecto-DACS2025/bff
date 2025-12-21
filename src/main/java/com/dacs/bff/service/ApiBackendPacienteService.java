package com.dacs.bff.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.PacienteDto.FrontResponse;

public interface ApiBackendPacienteService {

    // public List<PacienteDto.FrontResponse> getPacientesByIds(List<Long> ids);    norrar

    public PacienteDto.FrontResponse savePaciente(PacienteDto.FrontResponse paciente) throws Exception;

    public PacienteDto.FrontResponse updatePaciente(PacienteDto.BackResponse paciente) throws Exception;

    public ResponseEntity<Void> deletePaciente(Long id) throws Exception;

    public ResponseEntity<List<PacienteDto.FrontResponse>> getPacientesHospital(Integer cantidad);

    public PaginatedResponse<PacienteDto.FrontResponse> getPacientesByPage(int page, int size, String search);

    public PaginatedResponse<PacienteDto.FrontResponseLite> getPacientesLite(int page, int size, String search);
}
