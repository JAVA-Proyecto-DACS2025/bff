package com.dacs.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.AlumnoDto;
import com.dacs.bff.dto.BuildInfoDTO;
import com.dacs.bff.dto.CirugiaDTO;


@FeignClient(
		name = "apiBackendCirugiasClient", 
		url = "${feign.client.config.apiBackendCirugiasClient.url}",
		configuration = FeignConfig.class
		)

public interface ApiBackendCirugiasClient {

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
    

    @GetMapping("/cirugia")
    List<com.dacs.bff.dto.CirugiaDTO> cirugias();

    @PostMapping("/cirugia")
    CirugiaDTO save(@RequestBody CirugiaDTO cirugia);

    @PutMapping("/cirugia")
    CirugiaDTO update(@RequestBody CirugiaDTO cirugia);

    @DeleteMapping("/cirugia/{id}")
    CirugiaDTO delete(@PathVariable("id") Long id);
}
