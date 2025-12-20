package com.dacs.bff.api.client;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginatedResponse;

@FeignClient(name = "apiBackendCirugiasClient", url = "${feign.client.config.apiBackendCirugiasClient.url}", configuration = FeignConfig.class)

public interface ApiBackendCirugiasClient {

        @GetMapping("/ping")
        String ping();

        @GetMapping("/cirugia")
        PaginatedResponse<CirugiaDTO.BackResponse> cirugias(@RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name = "size", required = false) Integer size);

        @PostMapping("/cirugia")
        ResponseEntity<CirugiaDTO.BackResponse> create(@RequestBody CirugiaDTO.BackResponse cirugia);

        @PutMapping("/cirugia/{id}")
        ResponseEntity<CirugiaDTO.BackResponse> update(@PathVariable("id") String id,
                        @RequestBody CirugiaDTO.BackResponse cirugia);

        @DeleteMapping("/cirugia/{id}")
        ResponseEntity<Void> delete(@PathVariable("id") Long id);

        @GetMapping("/cirugia/{id}/equipo-medico")
        ResponseEntity<List<MiembroEquipoDTO.BackResponse>> getEquipoMedico(@PathVariable("id") Long id);

        @PostMapping("/cirugia/{id}/equipo-medico")
        ResponseEntity<List<MiembroEquipoDTO.BackResponse>> saveEquipoMedico(@PathVariable("id") Long id,
                        @RequestBody List<MiembroEquipoDTO.Create> miembros);

        @GetMapping("/cirugia/horarios-disponibles")
        ResponseEntity<List<LocalDateTime>> getTurnosDisponibles(
                        @RequestParam(name = "cantidadProximosDias") int cantidadProximosDias,
                        @RequestParam(name = "servicioId") Long servicioId);

        @GetMapping("/cirugia/servicios")
        ResponseEntity<List<com.dacs.bff.dto.ServicioDto>> getServicios();
}
