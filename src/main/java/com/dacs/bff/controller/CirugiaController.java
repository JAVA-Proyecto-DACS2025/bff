package com.dacs.bff.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.PaginatedResponse;

import com.dacs.bff.dto.ServicioDto;
// import com.dacs.bff.dto.CirugiaPageResponse;
import com.dacs.bff.service.ApiBackendCirugiaService;
import com.dacs.bff.util.ApiResponseBuilder;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@RestController
@RequestMapping("/cirugia")
public class CirugiaController {

    @Autowired
    private ApiBackendCirugiaService cirugiaService;

    @GetMapping("")                                         //Agregar para manejar filtrado por otros campos
    public ResponseEntity<ApiResponse<PaginatedResponse<CirugiaDTO.FrontResponse>>> getAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {
        try {
            PaginatedResponse<CirugiaDTO.FrontResponse> resp = cirugiaService.getCirugias(page, size);
            return ApiResponseBuilder.okWithPagination(resp);
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener cirugías: " + e.getMessage());
        }
    }

    @GetMapping("/por-fechas")
    public ResponseEntity<ApiResponse<List<CirugiaDTO.FrontResponse>>> getBetweenDates(@RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
    
            ResponseEntity<List<CirugiaDTO.FrontResponse>> response = cirugiaService.getBetweenDates(fechaInicio, fechaFin);
            return ApiResponseBuilder.ok(response.getBody());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener la cirugía: " + e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CirugiaDTO.FrontResponse>> create(@RequestBody CirugiaDTO.FrontRequest cirugiaDTO)
            throws Exception {
        try {
            ResponseEntity<CirugiaDTO.FrontResponse> response = cirugiaService.createCirugia(cirugiaDTO);
            return ApiResponseBuilder.created(response.getBody(), "Cirugia creada exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al crear la cirugía: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CirugiaDTO.FrontResponse>> update(@PathVariable String id,
            @RequestBody CirugiaDTO.FrontRequest cirugiaDTO) throws Exception {
        try {
            ResponseEntity<CirugiaDTO.FrontResponse> data = cirugiaService.updateCirugia(id, cirugiaDTO);
            return ApiResponseBuilder.ok(data.getBody(), "Cirugia actualizada exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al actualizar la cirugía: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable @NotNull Long id) {
        try {
            cirugiaService.deleteCirugia(id);
            return ApiResponseBuilder.ok(null, "Cirugia eliminada exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al eliminar la cirugia: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/equipo-medico")
    public ResponseEntity<ApiResponse<List<MiembroEquipoDTO.Response>>> getEquipoMedico(@PathVariable Long id) {
        try {
            ResponseEntity<List<MiembroEquipoDTO.Response>> response = cirugiaService.getEquipoMedico(id);
            return ApiResponseBuilder.ok(response.getBody());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener equipo médico: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/equipo-medico")
    public ResponseEntity<ApiResponse<List<MiembroEquipoDTO.Response>>> postEquipoMedico(@PathVariable Long id,
            @RequestBody List<MiembroEquipoDTO.Create> miembros) {
        try {
            ResponseEntity<List<MiembroEquipoDTO.Response>> response = cirugiaService.saveEquipoMedico(miembros, id);
            return ApiResponseBuilder.ok(response.getBody(), "Equipo medico guardado exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al guardar equipo médico: " + e.getMessage());
        }
    }

    @GetMapping("/horarios-disponibles")
    public ResponseEntity<ApiResponse<List<LocalDateTime>>> getHorariosDisponibles(
            @RequestParam int cantidadProximosDias, @RequestParam Long servicioId) {
        try {
            ResponseEntity<List<LocalDateTime>> horarios = cirugiaService.getTurnosDisponibles(cantidadProximosDias,
                    servicioId);
            return ApiResponseBuilder.ok(horarios.getBody());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener horarios disponibles: " + e.getMessage());
        }
    }

    @GetMapping("/servicios")
    public ResponseEntity<ApiResponse<List<ServicioDto>>> getServicios() {
        try {
            ResponseEntity<List<ServicioDto>> servicios = cirugiaService.getServicios();
            return ApiResponseBuilder.ok(servicios.getBody());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener servicios: " + e.getMessage());
        }
    }
}
