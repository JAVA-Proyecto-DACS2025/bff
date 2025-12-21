package com.dacs.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.dto.PacienteExternoDto;

@FeignClient(name = "apiConectorClient", url = "${feign.client.config.apiconectorclient.url}", configuration = FeignConfig.class)
public interface ApiConectorClient {

	@GetMapping("/ping")
	String ping();

	@GetMapping("/api/external/paciente")
	PacienteExternoDto getPacientesHospital(@RequestParam("cantidad") int cantidad);
}
