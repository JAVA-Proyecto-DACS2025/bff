package com.dacs.bff.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendCirugiasClient;

import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.util.CirugiaMapper;


@Service
public class ApiBackendCirugiaServiceImpl implements ApiBackendCirugiaService {

	@Autowired
	private ApiBackendCirugiasClient apiBackendClient;

	@Autowired
	private CirugiaMapper cirugiaMapper;

	@Override
	public PaginatedResponse<CirugiaDTO.FrontResponse> getCirugias(Integer page, Integer size) {
		PaginatedResponse<CirugiaDTO.BackResponse> backResp = apiBackendClient.cirugias(page, size);

		// mapear cada elemento de la lista
		List<CirugiaDTO.FrontResponse> frontList = backResp.getContent().stream()
				.map(item -> cirugiaMapper.toFrontResponse(item))  // usar instancia inyectada
				.collect(java.util.stream.Collectors.toList());

		// construir response con la lista mapeada
		PaginatedResponse<CirugiaDTO.FrontResponse> frontResp = new PaginatedResponse<>();
		frontResp.setContent(frontList);
		frontResp.setNumber(backResp.getNumber());
		frontResp.setSize(backResp.getSize());
		frontResp.setTotalElements(backResp.getTotalElements());
		frontResp.setTotalPages(backResp.getTotalPages());

		return frontResp;
	}

	@Override
	public CirugiaDTO.FrontResponse createCirugia(CirugiaDTO.Create cirugia) throws Exception {

		CirugiaDTO.BackResponse backResp = apiBackendClient.create(cirugia);
		return cirugiaMapper.toFrontResponse(backResp);
	}

	@Override
	public CirugiaDTO.FrontResponse updateCirugia(String id, CirugiaDTO.Update cirugia) throws Exception {
		CirugiaDTO.BackResponse backResp = apiBackendClient.update(id, cirugia);
		return cirugiaMapper.toFrontResponse(backResp);
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

		return apiBackendClient.saveEquipoMedico(id, miembros);
	}
}
