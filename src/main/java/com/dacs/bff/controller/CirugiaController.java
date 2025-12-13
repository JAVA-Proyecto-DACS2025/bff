package com.dacs.bff.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.Pagination;
// import com.dacs.bff.dto.CirugiaPageResponse;
import com.dacs.bff.service.ApiBackendCirugiaService;
import com.dacs.bff.util.ApiResponseBuilder;

import feign.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@RequestMapping("/cirugia")
public class CirugiaController {

    @Autowired
    private ApiBackendCirugiaService cirugiaService;
    

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CirugiaDTO.FrontResponse>>> getAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {
        PaginatedResponse<CirugiaDTO.FrontResponse> resp = cirugiaService.getCirugias(page, size);
        return ApiResponseBuilder.okWithPagination(resp);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CirugiaDTO.FrontResponse>> create(@RequestBody CirugiaDTO.Create cirugiaDTO)
            throws Exception {
        CirugiaDTO.FrontResponse data = cirugiaService.createCirugia(cirugiaDTO);
        return ApiResponseBuilder.created(data, "Cirugia creada exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CirugiaDTO.FrontResponse>> update(@PathVariable String id,
            @RequestBody CirugiaDTO.Update cirugiaDTO) throws Exception {

        ResponseEntity<CirugiaDTO.FrontResponse> data = cirugiaService.updateCirugia(id, cirugiaDTO);
        return ApiResponseBuilder.ok(data.getBody(), "Cirugia actualizada exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            cirugiaService.deleteCirugia(id);
            return ApiResponseBuilder.ok(null, "Cirugia eliminada exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al eliminar la cirugia: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/equipo-medico")
    public ResponseEntity<ApiResponse<List<MiembroEquipoDTO.Response>>> getEquipoMedico(@PathVariable Long id) {
        List<MiembroEquipoDTO.Response> data = cirugiaService.getEquipoMedico(id).stream().map(back -> {
            MiembroEquipoDTO.Response dto = new MiembroEquipoDTO.Response();
            dto.setCirugiaId(back.getCirugiaId());
            dto.setPersonalId(back.getPersonal().getId());
            dto.setLegajo(back.getPersonal().getLegajo());
            dto.setNombre(back.getPersonal().getNombre());
            dto.setRol(back.getRol());
            return dto;
        }).toList();
        return ApiResponseBuilder.ok(data);
    }

    @PostMapping("/{id}/equipo-medico")
    public ResponseEntity<ApiResponse<List<MiembroEquipoDTO.Response>>> postEquipoMedico(@PathVariable Long id,
            @RequestBody List<MiembroEquipoDTO.Create> miembros) {
        List<MiembroEquipoDTO.Response> data = cirugiaService.saveEquipoMedico(miembros, id).stream().map(back -> {
            MiembroEquipoDTO.Response dto = new MiembroEquipoDTO.Response();
            dto.setCirugiaId(back.getCirugiaId());
            dto.setPersonalId(back.getPersonal().getId());
            dto.setLegajo(back.getPersonal().getLegajo());
            dto.setNombre(back.getPersonal().getNombre());
            dto.setRol(back.getRol());
            return dto;
        }).toList();
        return ApiResponseBuilder.ok(data, "Equipo medico guardado exitosamente");
    }

}
