package com.dacs.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.AlumnoDto;
import com.dacs.bff.dto.BuildInfoDTO;
import com.dacs.bff.dto.CirugiaRequestDTO;
import com.dacs.bff.dto.CirugiaResponseDTO;
import com.dacs.bff.dto.PaginatedResponse;

@FeignClient(name = "apiBackendCirugiasClient", url = "${feign.client.config.apiBackendCirugiasClient.url}", configuration = FeignConfig.class)

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
    PaginatedResponse<CirugiaResponseDTO> cirugias(@RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size);

    @PostMapping("/cirugia")
    CirugiaRequestDTO save(@RequestBody CirugiaRequestDTO cirugia);

    @PutMapping("/cirugia")
    CirugiaRequestDTO update(@RequestBody CirugiaRequestDTO cirugia);

    @DeleteMapping("/cirugia/{id}")
    CirugiaRequestDTO delete(@PathVariable("id") Long id);
}
