package com.dacs.bff.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendCirugiasClient;


@Service
public class ApiBackendServiceImpl implements ApiBackendService{

	@Autowired
	private ApiBackendCirugiasClient apiBackendClient;
	
	@Override
	public String ping() {
		return apiBackendClient.ping();
	}
}
