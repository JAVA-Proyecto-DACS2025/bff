package com.dacs.bff.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendPacientesClient;
import com.dacs.bff.api.client.ApiConectorClient;
import com.dacs.bff.dto.PacienteDto;
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

        PacienteDto.BackResponse backResponse = apiBackendPacienteClient.save(modelMapper.map(paciente, PacienteDto.BackResponse.class));
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
    public List<PacienteDto.FrontResponse> getPacientesByIds(List<Long> pacientesIds) {
        return apiBackendPacienteClient.pacientes(pacientesIds).stream()
                .map(p -> modelMapper.map(p, PacienteDto.FrontResponse.class))
                .toList();
    }

    @Override
    public List<PacienteDto.FrontResponse> getPacientesHospital(Integer cantidad) {
        List<PacienteDto.ApiHospitalResponse> conectorRespone = apiConectorClient.getPacientesHospital(cantidad);
        return conectorRespone.stream()
                .map(PacienteMapper::fromApiHospitalResponse)
                .toList();
    }

    @Override
    public PaginatedResponse<PacienteDto.FrontResponse> getPacientesByPage(int page, int size, String search) {
        // Llamar al backend con search
        PaginatedResponse<PacienteDto.BackResponse> backendResponse = 
            apiBackendPacienteClient.pacientesByPage(page, size, search);
        
        // Mapear contenido
        List<PacienteDto.FrontResponse> mappedContent = backendResponse.getContent().stream()
            .map(p -> modelMapper.map(p, PacienteDto.FrontResponse.class))
            .toList();
        
        // Crear respuesta paginada
        PaginatedResponse<PacienteDto.FrontResponse> response = new PaginatedResponse<>();
        response.setContent(mappedContent);
        response.setTotalElements(backendResponse.getTotalElements());
        response.setTotalPages(backendResponse.getTotalPages());
        response.setNumber(backendResponse.getNumber());
        response.setSize(backendResponse.getSize());
        
        return response;
    }

    @Override
    public PaginatedResponse<PacienteDto.FrontResponseLite> getPacientesLite(int page, int size, String search) {
        PaginatedResponse<PacienteDto.FrontResponse> paginatedData = getPacientesByPage(page, size, search);
        
        List<PacienteDto.FrontResponseLite> mappedContent = paginatedData.getContent().stream()
            .map(p -> modelMapper.map(p, PacienteDto.FrontResponseLite.class))
            .toList();
        
        PaginatedResponse<PacienteDto.FrontResponseLite> response = new PaginatedResponse<>();
        response.setContent(mappedContent);
        response.setTotalElements(paginatedData.getTotalElements());
        response.setTotalPages(paginatedData.getTotalPages());  
        response.setNumber(paginatedData.getNumber());
        response.setSize(paginatedData.getSize());
        return response;
    }
}
