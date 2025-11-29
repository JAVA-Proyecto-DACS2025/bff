package com.dacs.bff.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendQuirofanoClient;
import com.dacs.bff.dto.QuirofanoDto;


@Service
public class ApiBackendQuirofanoServiceImpl implements ApiBackendQuirofanoService {

    @Autowired
    private ApiBackendQuirofanoClient apiBackendQuirofanoClient;

    @Override
    public QuirofanoDto saveQuirofano(QuirofanoDto quirofano) throws Exception {
        return apiBackendQuirofanoClient.save(quirofano);
    }

    @Override
    public QuirofanoDto updateQuirofano(QuirofanoDto quirofano) throws Exception {
        return apiBackendQuirofanoClient.update(quirofano);
    }
    
    @Override
    public QuirofanoDto deleteQuirofano(Long id) throws Exception {
        return apiBackendQuirofanoClient.delete(id);
    }

    @Override
    public List<QuirofanoDto> getQuirofanos() {
        return apiBackendQuirofanoClient.quirofanos();
    }

}
