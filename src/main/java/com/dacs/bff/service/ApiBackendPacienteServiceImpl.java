package com.dacs.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendPacientesClient;
import com.dacs.bff.dto.PacienteDto;

@Service
public class ApiBackendPacienteServiceImpl implements ApiBackendPacienteService {

    @Autowired
    private ApiBackendPacientesClient apiBackendPacienteClient;

    @Override
    public PacienteDto savePaciente(PacienteDto paciente) throws Exception {
        return apiBackendPacienteClient.save(paciente);
    }

    @Override
    public PacienteDto updatePaciente(PacienteDto paciente) throws Exception {
        return apiBackendPacienteClient.update(paciente);
    }

    @Override
    public PacienteDto deletePaciente(Long id) throws Exception {
        return apiBackendPacienteClient.delete(id);
    }

    @Override
    public List<PacienteDto> getPacientes() {
        return apiBackendPacienteClient.pacientes(null);
    }

    @Override
    public List<PacienteDto> getPacientes(String search) {
        return apiBackendPacienteClient.pacientes(search);
    }
}
