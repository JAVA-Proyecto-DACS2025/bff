package com.dacs.bff.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.service.ApiBackendPacienteService;
import com.dacs.bff.util.ApiResponseBuilder;

import ch.qos.logback.core.model.Model;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private ApiBackendPacienteService pacienteService;

    @Autowired
    private ModelMapper modelMapper;

    // Paginación + filtro opcional
    @GetMapping("")
    public ResponseEntity<ApiResponse<PaginatedResponse<PacienteDto.FrontResponse>>> getPacientes(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "16") int size,
            @RequestParam(name = "search", required = false) String search) {

        PaginatedResponse<PacienteDto.FrontResponse> data = pacienteService.getPacientesByPage(page, size, search);

        return ApiResponseBuilder.okWithPagination(data);
    }

    @GetMapping("/lite")
    public ResponseEntity<ApiResponse<PaginatedResponse<PacienteDto.FrontResponseLite>>> getPacientesLite(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "16") int size,
            @RequestParam(name = "search", required = false) String search) {

        PaginatedResponse<PacienteDto.FrontResponseLite> data = pacienteService.getPacientesLite(page, size, search);;
        return ApiResponseBuilder.okWithPagination(data);
    }

    @PostMapping("")
    public ResponseEntity<PacienteDto.FrontResponse> save(@RequestBody PacienteDto.FrontResponse pacienteDto)
            throws Exception {
        PacienteDto.FrontResponse data = pacienteService.savePaciente(pacienteDto);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    @GetMapping("/hospital")
    public List<PacienteDto.FrontResponse> getPacientesHospital(@RequestParam("cantidad") int cantidad) {
        return pacienteService.getPacientesHospital(cantidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) throws Exception {
        ResponseEntity<Void> backResponse = pacienteService.deletePaciente(id);
        return new ResponseEntity<>(backResponse.getStatusCode());
    }
}
