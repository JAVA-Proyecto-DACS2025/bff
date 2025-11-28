package com.dacs.bff.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendCirugiasClient;
import com.dacs.bff.dto.AlumnoDto;
import com.dacs.bff.dto.CirugiaDTO;

@Service
public class ApiBackendCirugiaServiceImpl implements ApiBackendCirugiaService{

	@Autowired
	private ApiBackendCirugiasClient apiBackendClient;
	
	
	@Override
	public com.dacs.bff.dto.CirugiaPageResponse getCirugias(Integer page, Integer size) {
		return apiBackendClient.cirugias(page, size);
	}

	@Override
	public CirugiaDTO saveCirugia(CirugiaDTO cirugia) throws Exception {
		//TODO validar parametro y lanzar exepcion
		return apiBackendClient.save(cirugia);
	}

	@Override
	public CirugiaDTO updateCirugia(CirugiaDTO cirugia) throws Exception {
		//TODO validar parametro y lanzar exepcion
		return apiBackendClient.update(cirugia);
	}

	@Override
	public CirugiaDTO deleteCirugia(Long id) throws Exception {
		//TODO validar parametro y lanzar exepcion
		return apiBackendClient.delete(id);
	}
}

