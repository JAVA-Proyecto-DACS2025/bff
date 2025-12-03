package com.dacs.bff.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.CirugiaResponseDTO;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.CirugiaRequestDTO;
import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.Pagination;
// import com.dacs.bff.dto.CirugiaPageResponse;
import com.dacs.bff.service.ApiBackendCirugiaService;
import com.dacs.bff.service.ApiBackendPacienteService;

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
    @Autowired
    private ApiBackendPacienteService pacienteService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CirugiaResponseDTO>>> getAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {
        PaginatedResponse<CirugiaResponseDTO> backend = cirugiaService.getCirugias(page, size);

        // Construir ApiResponse con paginación
        ApiResponse<List<CirugiaResponseDTO>> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setData(backend.getContent());
        resp.setMessage(null);
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());

        Pagination p = new Pagination();
        p.setPage(backend.getNumber());
        p.setPageSize(backend.getSize());
        p.setTotalItems(backend.getTotalElements());
        p.setTotalPages(backend.getTotalPages());
        p.setHasNext(backend.getNumber() < backend.getTotalPages() - 1);
        p.setHasPrevious(backend.getNumber() > 0);
        resp.setPagination(p);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CirugiaResponseDTO>> save(@RequestBody CirugiaRequestDTO cirugiaDTO)
            throws Exception {
        CirugiaResponseDTO data = cirugiaService.saveCirugia(cirugiaDTO);

        ApiResponse<CirugiaResponseDTO> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setData(data);
        resp.setMessage("Cirugia creada exitosamente");
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CirugiaResponseDTO>> update(@PathVariable String id,
            @RequestBody CirugiaRequestDTO cirugiaDTO) throws Exception {

        CirugiaResponseDTO data = cirugiaService.updateCirugia(id, cirugiaDTO);
        ApiResponse<CirugiaResponseDTO> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setData(data);
        resp.setMessage("Cirugia actualizada exitosamente");
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        ApiResponse<Void> resp = new ApiResponse<>();
        try {
            cirugiaService.deleteCirugia(id);
            resp.setSuccess(true);
            resp.setData(null);
            resp.setMessage("Cirugia eliminada exitosamente");
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setData(null);
            resp.setMessage("Error al eliminar la cirugia: " + e.getMessage());
        }
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{id}/equipo-medico")
    public ResponseEntity<ApiResponse<List<MiembroEquipoDTO>>> getEquipoMedico(@PathVariable Long id) {
        List<MiembroEquipoDTO.BackResponse> backResponse = cirugiaService.getEquipoMedico(id);
        ApiResponse<List<MiembroEquipoDTO>> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setData(backResponse.stream().map(back -> {
            MiembroEquipoDTO dto = new MiembroEquipoDTO();
            dto.setCirugiaId(back.getCirugiaId());
            dto.setPersonalId(back.getPersonal().getId());
            dto.setLegajo(back.getPersonal().getLegajo());
            dto.setNombre(back.getPersonal().getNombre());
            dto.setRol(back.getRol());
            //dto.setFechaAsignacion(back.getFechaAsignacion());   En caso de querer enviar la fecha de asignacion al front
            return dto;
        }).toList());
        resp.setMessage(null);
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/{id}/equipo-medico")
    public ResponseEntity<ApiResponse<List<MiembroEquipoDTO>>> postEquipoMedico(@PathVariable Long id,
            @RequestBody List<MiembroEquipoDTO> miembros) {
        List<MiembroEquipoDTO.BackResponse> backResponse = cirugiaService.saveEquipoMedico(miembros, id);
        
        List<MiembroEquipoDTO> frontResponse = backResponse.stream().map(back -> {
            MiembroEquipoDTO dto = new MiembroEquipoDTO();
            dto.setCirugiaId(back.getCirugiaId());
            dto.setPersonalId(back.getPersonal().getId());
            dto.setLegajo(back.getPersonal().getLegajo());
            dto.setNombre(back.getPersonal().getNombre());
            dto.setRol(back.getRol());
            //dto.setFechaAsignacion(back.getFechaAsignacion());   En caso de querer enviar la fecha de asignacion al front
            return dto;
        }).toList();

        ApiResponse<List<MiembroEquipoDTO>> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setData(frontResponse);
        resp.setMessage("Equipo medico guardado exitosamente");
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
