package com.dacs.bff.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.CirugiaDTO;
// import com.dacs.bff.dto.CirugiaPageResponse;
import com.dacs.bff.service.ApiBackendCirugiaService;

import lombok.extern.slf4j.Slf4j;

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

    @GetMapping("")
    public ResponseEntity<com.dacs.bff.dto.ApiResponse<java.util.List<CirugiaDTO>>> getAll(
            @org.springframework.web.bind.annotation.RequestParam(name = "page", required = false) Integer page,
            @org.springframework.web.bind.annotation.RequestParam(name = "size", required = false) Integer size) {
        log.info("Obteniendo lista de cirugias (page={}, size={})", page, size);
        com.dacs.bff.dto.CirugiaPageResponse backend = cirugiaService.getCirugias(page, size);

        com.dacs.bff.dto.ApiResponse<java.util.List<CirugiaDTO>> resp = new com.dacs.bff.dto.ApiResponse<>();
        resp.setSuccess(true);
        resp.setData(backend.getContent());
        resp.setMessage(null);
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());

        com.dacs.bff.dto.Pagination p = new com.dacs.bff.dto.Pagination();
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
    public ResponseEntity<CirugiaDTO> save (@RequestBody CirugiaDTO cirugiaDTO) throws Exception {
        log.info("Creando nueva cirugia");
        CirugiaDTO data = cirugiaService.saveCirugia(cirugiaDTO);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }
}
