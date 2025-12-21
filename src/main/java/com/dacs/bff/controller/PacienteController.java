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

    @GetMapping("")
    public ResponseEntity<ApiResponse<PaginatedResponse<PacienteDto.FrontResponse>>> getPacientes(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "16") int size,
            @RequestParam(name = "search", required = false) String search) {
        try {
            PaginatedResponse<PacienteDto.FrontResponse> data = pacienteService.getPacientesByPage(page, size, search);
            return ApiResponseBuilder.okWithPagination(data);
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener pacientes: " + e.getMessage());
        }
    }

    @GetMapping("/lite")
    public ResponseEntity<ApiResponse<PaginatedResponse<PacienteDto.FrontResponseLite>>> getPacientesLite(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "16") int size,
            @RequestParam(name = "search", required = false) String search) {
        try {
            PaginatedResponse<PacienteDto.FrontResponseLite> data = pacienteService.getPacientesLite(page, size, search);
            return ApiResponseBuilder.okWithPagination(data);
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener pacientes lite: " + e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<PacienteDto.FrontResponse>> create(@RequestBody PacienteDto.FrontResponse pacienteDto)
            throws Exception {
        try {
            PacienteDto.FrontResponse data = pacienteService.savePaciente(pacienteDto);
            return ApiResponseBuilder.created(data, "Paciente creado exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al crear el paciente: " + e.getMessage());
        }
    }

    // Obtiene los pacientes desde la api externa del conector
    @GetMapping("/hospital")
    public ResponseEntity<ApiResponse<List<PacienteDto.FrontResponse>>> getPacientesHospital(@RequestParam("cantidad") int cantidad) {
        try {
            ResponseEntity<List<PacienteDto.FrontResponse>> response = pacienteService.getPacientesHospital(cantidad);
            return ApiResponseBuilder.ok(response.getBody(), "Pacientes hospital obtenidos exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener pacientes hospital: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) throws Exception {
        try {
            pacienteService.deletePaciente(id);
            return ApiResponseBuilder.ok(null, "Paciente eliminado exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al eliminar el paciente: " + e.getMessage());
        }
    }
}
