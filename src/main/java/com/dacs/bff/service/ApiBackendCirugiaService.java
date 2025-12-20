package com.dacs.bff.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.CirugiaDTO.BackResponse;
import com.dacs.bff.dto.CirugiaDTO.FrontResponse;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.ServicioDto;

public interface ApiBackendCirugiaService {

    public PaginatedResponse<CirugiaDTO.FrontResponse> getCirugias(Integer page, Integer size);

    public CirugiaDTO.FrontResponse createCirugia(CirugiaDTO.BackResponse cirugia) throws Exception;

    public ResponseEntity<Void> deleteCirugia(Long id) throws Exception;

    public ResponseEntity<List<MiembroEquipoDTO.BackResponse>> getEquipoMedico(Long id);

    public ResponseEntity<List<MiembroEquipoDTO.BackResponse>> saveEquipoMedico(List<MiembroEquipoDTO.Create> miembros,
            Long id);

    public ResponseEntity<List<LocalDateTime>> getTurnosDisponibles(int cantidadProximosDias, Long servicioId);

    public ResponseEntity<List<ServicioDto>> getServicios();

    public ResponseEntity<CirugiaDTO.FrontResponse> updateCirugia(String id, CirugiaDTO.BackResponse cirugia) throws Exception;
}
