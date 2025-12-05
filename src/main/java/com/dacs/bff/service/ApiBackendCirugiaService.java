package com.dacs.bff.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginatedResponse;

public interface ApiBackendCirugiaService {

    public PaginatedResponse<CirugiaDTO.Response> getCirugias(Integer page, Integer size);

    public CirugiaDTO.Response createCirugia(CirugiaDTO.Create cirugia) throws Exception;

    public  CirugiaDTO.Response updateCirugia(String id, CirugiaDTO.Update cirugia) throws Exception;

    public  ResponseEntity<Void> deleteCirugia(Long id) throws Exception;

    public List<MiembroEquipoDTO.BackResponse> getEquipoMedico(Long id);

    public List<MiembroEquipoDTO.BackResponse> saveEquipoMedico(List<MiembroEquipoDTO.Create> miembros, Long id);
}