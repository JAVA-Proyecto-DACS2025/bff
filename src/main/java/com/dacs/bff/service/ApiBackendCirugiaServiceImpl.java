package com.dacs.bff.service;


import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendCirugiasClient;

import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.ServicioDto;
import com.dacs.bff.util.CirugiaMapper;


@Service
public class ApiBackendCirugiaServiceImpl implements ApiBackendCirugiaService {

	@Autowired
	private ApiBackendCirugiasClient apiBackendCirugiaClient;

	@Autowired
	private CirugiaMapper cirugiaMapper;

	@Override
	public PaginatedResponse<CirugiaDTO.FrontResponse> getCirugias(Integer page, Integer size) {
		PaginatedResponse<CirugiaDTO.BackResponse> backResp = apiBackendCirugiaClient.cirugias(page, size);

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

		CirugiaDTO.BackResponse backResp = apiBackendCirugiaClient.create(cirugia);
		return cirugiaMapper.toFrontResponse(backResp);
	}

	@Override
	public ResponseEntity<CirugiaDTO.FrontResponse> updateCirugia(String id, CirugiaDTO.Update cirugia) throws Exception {
		ResponseEntity<CirugiaDTO.BackResponse> backResp = apiBackendCirugiaClient.update(id, cirugia);
		return ResponseEntity.status(backResp.getStatusCode()).body(cirugiaMapper.toFrontResponse(backResp.getBody()));
	}

	@Override
	public ResponseEntity<Void> deleteCirugia(Long id) throws Exception {

		return apiBackendCirugiaClient.delete(id);
	}

	@Override
	public ResponseEntity<List<MiembroEquipoDTO.BackResponse>> getEquipoMedico(Long id) {

		return apiBackendCirugiaClient.getEquipoMedico(id);
	}

	@Override
	public ResponseEntity<List<MiembroEquipoDTO.BackResponse>> saveEquipoMedico(List<MiembroEquipoDTO.Create> miembros, Long id) {

		return apiBackendCirugiaClient.saveEquipoMedico(id, miembros);
	}

	@Override
	public ResponseEntity<List<LocalDateTime>> getTurnosDisponibles(int cantidadProximosDias, Long servicioId) {
		return apiBackendCirugiaClient.getTurnosDisponibles(cantidadProximosDias, servicioId);
	}

	@Override
	public ResponseEntity<List<ServicioDto>> getServicios() {
		
		return apiBackendCirugiaClient.getServicios();
	}
}
