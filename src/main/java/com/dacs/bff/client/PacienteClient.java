package com.dacs.bff.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.dacs.bff.dto.PacienteDto;

@FeignClient(name = "backend", contextId = "pacienteClient", url = "${backend.base-url:http://localhost:8080}")
public interface PacienteClient {

    @GetMapping("/pacientes/{id}")
    PacienteDto.BackResponse getById(@PathVariable("id") Long id);

    @GetMapping("/pacientes")
    com.dacs.bff.dto.PaginacionDto.Response<PacienteDto.BackResponse> list(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "16") int size,
            @RequestParam(name = "search", required = false) String search
    );

    @PostMapping("/pacientes")
    PacienteDto.BackResponse create(@RequestBody PacienteDto.Create input);

    @PutMapping("/pacientes")
    PacienteDto.BackResponse update(@RequestBody PacienteDto.Update input);

    @PutMapping("/pacientes/{id}/activate")
    void activate(@PathVariable("id") Long id);

    @PutMapping("/pacientes/{id}/deactivate")
    void deactivate(@PathVariable("id") Long id);
}
