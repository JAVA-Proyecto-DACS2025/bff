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
import com.dacs.bff.dto.PacienteDto;

@FeignClient(
		name = "apiBackendPacientesClient", 
		url = "${feign.client.config.apiBackendPacientesClient.url}",
		configuration = FeignConfig.class
		)

public interface ApiBackendPacientesClient {

    @GetMapping("/paciente")
    List<com.dacs.bff.dto.PacienteDto> pacientes();

    @PostMapping("/paciente")
    PacienteDto save(@RequestBody PacienteDto paciente);
    
    @PutMapping("/paciente")
    PacienteDto update(@RequestBody PacienteDto paciente);

    @DeleteMapping("/paciente/{id}")
    PacienteDto delete(@PathVariable("id") Long id);
}
