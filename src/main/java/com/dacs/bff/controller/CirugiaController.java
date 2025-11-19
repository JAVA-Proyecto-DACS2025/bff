package com.dacs.bff.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.service.ApiBackendCirugiaService;

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

    @GetMapping("")
    public ResponseEntity<List<CirugiaDTO>> getAll() {
        log.info("Obteniendo lista de cirugias");
        List<CirugiaDTO> data = cirugiaService.getCirugias();
        return new ResponseEntity<>(data, HttpStatus.OK);
    } 

    @PostMapping("")
    public ResponseEntity<CirugiaDTO> create (@RequestBody CirugiaDTO cirugiaDTO) throws Exception {
        log.info("Creando nueva cirugia");
        CirugiaDTO data = cirugiaService.saveCirugia(cirugiaDTO);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }
}
