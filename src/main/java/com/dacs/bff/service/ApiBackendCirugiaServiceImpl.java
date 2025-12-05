package com.dacs.bff.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendCirugiasClient;
import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginatedResponse;

@Service
public class ApiBackendCirugiaServiceImpl implements ApiBackendCirugiaService {

	@Autowired
	private ApiBackendCirugiasClient apiBackendClient;

	@Override
	public PaginatedResponse<CirugiaDTO.Response> getCirugias(Integer page, Integer size) {
		return apiBackendClient.cirugias(page, size);
	}

	@Override
	public CirugiaDTO.Response createCirugia(CirugiaDTO.Create cirugia) throws Exception {
		// TODO validar parametro y lanzar exepcion
		return apiBackendClient.create(cirugia);
	}

	@Override
	public CirugiaDTO.Response updateCirugia(String id, CirugiaDTO.Update cirugia) throws Exception {
		return apiBackendClient.update(id, cirugia);
	}

	@Override
	public ResponseEntity<Void> deleteCirugia(Long id) throws Exception {

		return apiBackendClient.delete(id);
	}

	@Override
	public List<MiembroEquipoDTO.BackResponse> getEquipoMedico(Long id) {

		return apiBackendClient.getEquipoMedico(id);
	}

	@Override
	public List<MiembroEquipoDTO.BackResponse> saveEquipoMedico(List<MiembroEquipoDTO.Create> miembros, Long id) {

		return  apiBackendClient.saveEquipoMedico(id, miembros);
	}
}
