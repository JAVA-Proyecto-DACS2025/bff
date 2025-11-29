package com.dacs.bff.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendCirugiasClient;
import com.dacs.bff.dto.CirugiaRequestDTO;
import com.dacs.bff.dto.CirugiaResponseDTO;
import com.dacs.bff.dto.PaginatedResponse;

@Service
public class ApiBackendCirugiaServiceImpl implements ApiBackendCirugiaService{

	@Autowired
	private ApiBackendCirugiasClient apiBackendClient;
	
	
	@Override
	public PaginatedResponse<CirugiaResponseDTO> getCirugias(Integer page, Integer size) {
		return apiBackendClient.cirugias(page, size);
	}

	@Override
	public CirugiaResponseDTO saveCirugia(CirugiaRequestDTO cirugia) throws Exception {
		//TODO validar parametro y lanzar exepcion
		return apiBackendClient.save(cirugia);
	}

	@Override
	public CirugiaResponseDTO updateCirugia(CirugiaRequestDTO cirugia) throws Exception {
		//TODO validar parametro y lanzar exepcion
		return apiBackendClient.update(cirugia);
	}

	@Override
	public CirugiaResponseDTO deleteCirugia(Long id) throws Exception {
		//TODO validar parametro y lanzar exepcion
		return apiBackendClient.delete(id);
	}
}

