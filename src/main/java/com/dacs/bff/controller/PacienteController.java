package com.dacs.bff.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.service.ApiBackendPacienteService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<PacienteDto.FrontResponse>> getByIds(@RequestParam(name = "search", required = false) String search) {
        log.info("Obteniendo lista de pacientes (search={})", search);

        // obtener todos desde el servicio (el servicio actual acepta List<Long> ids;
        // pasamos null para obtener todos)
        List<PacienteDto.FrontResponse> data = pacienteService.getPacientesByIds((List<Long>) null);

        if (search == null || search.isBlank()) {
            return new ResponseEntity<>(data, HttpStatus.OK);
        }

        String s = search.toLowerCase();
        List<PacienteDto.FrontResponse> filtered = data.stream()
                .filter(p -> (p.getNombre() != null && p.getNombre().toLowerCase().contains(s))
                        || (p.getDni() != null && p.getDni().toLowerCase().contains(s)))
                .collect(Collectors.toList());

        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PacienteDto.FrontResponse> save(@RequestBody PacienteDto pacienteDto) throws Exception {
        log.info("Creando nuevo paciente");
        PacienteDto.FrontResponse data = pacienteService.savePaciente(pacienteDto);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    // obtiene personas cargadas en el hospital mediante la api externa
    @GetMapping("/hospital")
    public List<PacienteDto.FrontResponse> getPersonasHospital(@RequestParam("cantidad") int cantidad) {
        
        return pacienteService.getPacientesHospital(cantidad);
    }

}
