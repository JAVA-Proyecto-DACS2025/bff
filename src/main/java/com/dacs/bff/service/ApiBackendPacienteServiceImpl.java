package com.dacs.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendPacientesClient;
import com.dacs.bff.api.client.ApiConectorClient;
import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.util.PacienteMapper;

@Service
public class ApiBackendPacienteServiceImpl implements ApiBackendPacienteService {

    @Autowired
    private ApiBackendPacientesClient apiBackendPacienteClient;

    @Autowired
    private ApiConectorClient apiConectorClient;

    @Override
    public PacienteDto.FrontResponse savePaciente(PacienteDto paciente) throws Exception {
        return apiBackendPacienteClient.save(paciente);
    }

    @Override
    public PacienteDto.FrontResponse updatePaciente(PacienteDto paciente) throws Exception {
        return apiBackendPacienteClient.update(paciente);
    }

    @Override
    public PacienteDto.FrontResponse deletePaciente(Long id) throws Exception {
        return apiBackendPacienteClient.delete(id);
    }

    @Override
    public List<PacienteDto.FrontResponse> getPacientesByIds(List<Long> pacientesIds) {
        return apiBackendPacienteClient.pacientes(pacientesIds);

    }

    @Override
    public List<PacienteDto.FrontResponse> getPacientesHospital(Integer cantidad) {
        List<PacienteDto.ApiHospitalResponse> conectorRespone = apiConectorClient.getPacientesHospital(cantidad);
        return conectorRespone.stream()
                .map(PacienteMapper::fromApiHospitalResponse)
                .toList();
    }
}
