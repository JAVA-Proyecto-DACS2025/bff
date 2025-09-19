package com.dacs.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dacs.bff.dto.AlumnoDto;
import com.dacs.bff.dto.BuildInfoDTO;


@FeignClient(
			name = "apiBackendClient", 
			url = "${feign.client.config.apiBackendClient.url}"
			)

public interface ApiBackendClient {

    @GetMapping("/ping")
    String ping();
    
    @GetMapping("/version")
    BuildInfoDTO version();
    
    @GetMapping("/alumno")
    List<AlumnoDto> alumnos();
    
    @GetMapping("/alumno/{id}")
    AlumnoDto alumnoById(@PathVariable("id") Long id);
    
    @PostMapping("/alumno")
    AlumnoDto save(@RequestBody AlumnoDto alumno);
    
    @PutMapping("/alumno")
    AlumnoDto update(@RequestBody AlumnoDto alumno);
    
    @DeleteMapping("/alumno/{id}")
    AlumnoDto delete(@PathVariable("id") Long id);
}
