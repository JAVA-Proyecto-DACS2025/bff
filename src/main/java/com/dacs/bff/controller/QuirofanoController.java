package com.dacs.bff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.QuirofanoDto;
import com.dacs.bff.service.ApiBackendQuirofanoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/quirofano")
public class QuirofanoController {

    @Autowired
    private ApiBackendQuirofanoService quirofanoService;
    @GetMapping("")
    public ResponseEntity<List<QuirofanoDto>> getAll() {
        log.info("Obteniendo lista de quirofanos");
        List<QuirofanoDto> data = quirofanoService.getQuirofanos();
        return new ResponseEntity<>(data, HttpStatus.OK);
    } 

    @PostMapping("")
    public ResponseEntity<QuirofanoDto> create (@RequestBody QuirofanoDto quirofanoDto) throws Exception {
        log.info("Creando nuevo quirofano");
        QuirofanoDto data = quirofanoService.saveQuirofano(quirofanoDto);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }
}
