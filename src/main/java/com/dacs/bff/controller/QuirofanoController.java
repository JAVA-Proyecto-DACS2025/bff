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
    public ResponseEntity<com.dacs.bff.dto.ApiResponse<List<QuirofanoDto>>> getAll() {
        try {
            ResponseEntity<List<QuirofanoDto>> response = quirofanoService.getQuirofanos();
            return com.dacs.bff.util.ApiResponseBuilder.ok(response.getBody());
        } catch (Exception e) {
            return com.dacs.bff.util.ApiResponseBuilder.serverError("Error al obtener quirofanos: " + e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<com.dacs.bff.dto.ApiResponse<QuirofanoDto>> create (@RequestBody QuirofanoDto quirofanoDto) {
        try {
            ResponseEntity<QuirofanoDto> response = quirofanoService.saveQuirofano(quirofanoDto);
            return com.dacs.bff.util.ApiResponseBuilder.created(response.getBody(), "Quirofano creado exitosamente");
        } catch (Exception e) {
            return com.dacs.bff.util.ApiResponseBuilder.serverError("Error al crear quirofano: " + e.getMessage());
        }
    }
}
