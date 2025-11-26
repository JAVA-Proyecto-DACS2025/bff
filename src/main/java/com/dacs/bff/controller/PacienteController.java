package com.dacs.bff.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.service.ApiBackendPacienteService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<PacienteDto>> getAll(@RequestParam(name = "search", required = false) String search) {
        log.info("Obteniendo lista de pacientes (search={})", search);
        List<PacienteDto> data = pacienteService.getPacientes(search);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PacienteDto> save (@RequestBody PacienteDto pacienteDto) throws Exception {
        log.info("Creando nuevo paciente");
        PacienteDto data = pacienteService.savePaciente(pacienteDto);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

}
    

