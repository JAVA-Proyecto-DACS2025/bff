package com.dacs.bff.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendPacientesClient;
import com.dacs.bff.api.client.ApiConectorClient;
import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.dto.PacienteExternoDto;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.util.PacienteMapper;

@Service
public class ApiBackendPacienteServiceImpl implements ApiBackendPacienteService {

    @Autowired
    private ApiBackendPacientesClient apiBackendPacienteClient;

    @Autowired
    private ApiConectorClient apiConectorClient;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PacienteDto.FrontResponse savePaciente(PacienteDto.FrontResponse paciente) throws Exception {

        PacienteDto.BackResponse backResponse = apiBackendPacienteClient
                .save(modelMapper.map(paciente, PacienteDto.BackResponse.class));
        return modelMapper.map(backResponse, PacienteDto.FrontResponse.class);
    }

    @Override
    public PacienteDto.FrontResponse updatePaciente(PacienteDto.BackResponse paciente) throws Exception {
        return modelMapper.map(paciente, PacienteDto.FrontResponse.class);
    }

    @Override
    public ResponseEntity<Void> deletePaciente(Long id) throws Exception {
        return apiBackendPacienteClient.delete(id);
    }


    @Override
    public ResponseEntity<List<PacienteDto.FrontResponse>> getPacientesHospital(Integer cantidad) {
        PacienteExternoDto response = apiConectorClient.getPacientesHospital(cantidad);
        List<PacienteDto.FrontResponse> lista = response.getResults()
                .stream()
                .map(PacienteMapper::fromApiHospitalResponse)
                .toList();

        return ResponseEntity.ok(lista);
    }

        @Override
        public PaginatedResponse<PacienteDto.FrontResponse> getPacientesByPage(int page, int size, String search) {
        PaginatedResponse<PacienteDto.BackResponse> backendResponse = apiBackendPacienteClient.getPacienteS(page, size, search);
        List<PacienteDto.FrontResponse> mappedContent = backendResponse.getContent().stream()
            .map(p -> modelMapper.map(p, PacienteDto.FrontResponse.class))
            .toList();
        return com.dacs.bff.util.PaginatedResponseUtil.build(backendResponse, mappedContent);
        }

    @Override
    public PaginatedResponse<PacienteDto.FrontResponseLite> getPacientesLite(int page, int size, String search) {
        PaginatedResponse<PacienteDto.FrontResponse> paginatedData = getPacientesByPage(page, size, search);
        List<PacienteDto.FrontResponseLite> mappedContent = paginatedData.getContent().stream()
                .map(p -> modelMapper.map(p, PacienteDto.FrontResponseLite.class))
                .toList();
        return com.dacs.bff.util.PaginatedResponseUtil.build(paginatedData, mappedContent);
    }
}
