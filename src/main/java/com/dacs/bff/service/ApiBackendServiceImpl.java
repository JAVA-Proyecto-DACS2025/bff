package com.dacs.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendCirugiasClient;
import com.dacs.bff.dto.AlumnoDto;

@Service
public class ApiBackendServiceImpl implements ApiBackendService{

	@Autowired
	private ApiBackendCirugiasClient apiBackendClient;
	
	@Override
	public String ping() {
		return apiBackendClient.ping();
	}
}
