package com.dacs.bff.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.CirugiaResponseDTO;
import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.CirugiaRequestDTO;
import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.Pagination;
// import com.dacs.bff.dto.CirugiaPageResponse;
import com.dacs.bff.service.ApiBackendCirugiaService;
import com.dacs.bff.service.ApiBackendPacienteService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<CirugiaResponseDTO> save (@RequestBody CirugiaRequestDTO cirugiaDTO) throws Exception {
        log.info("Creando nueva cirugia");
        CirugiaResponseDTO data = cirugiaService.saveCirugia(cirugiaDTO);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }
}
