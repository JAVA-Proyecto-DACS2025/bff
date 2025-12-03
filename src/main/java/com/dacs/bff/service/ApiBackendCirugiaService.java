package com.dacs.bff.service;

import java.util.List;

import com.dacs.bff.dto.CirugiaRequestDTO;
import com.dacs.bff.dto.CirugiaResponseDTO;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginatedResponse;

public interface ApiBackendCirugiaService {

    public PaginatedResponse<CirugiaResponseDTO> getCirugias(Integer page, Integer size);

    public CirugiaResponseDTO saveCirugia(CirugiaRequestDTO cirugia) throws Exception;

    public CirugiaResponseDTO updateCirugia(String id, CirugiaRequestDTO cirugia) throws Exception;

    public CirugiaResponseDTO deleteCirugia(Long id) throws Exception;

    public List<MiembroEquipoDTO.BackResponse> getEquipoMedico(Long id);

    public List<MiembroEquipoDTO.BackResponse> saveEquipoMedico(List<MiembroEquipoDTO> miembros, Long id);
}