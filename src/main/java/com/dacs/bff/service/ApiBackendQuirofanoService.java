package com.dacs.bff.service;

import com.dacs.bff.dto.QuirofanoDto;

import java.util.List;

public interface ApiBackendQuirofanoService {
    public List<QuirofanoDto> getQuirofanos();
    
    public QuirofanoDto saveQuirofano(QuirofanoDto quirofano) throws Exception;

    public QuirofanoDto updateQuirofano(QuirofanoDto quirofano) throws Exception;
    
    public QuirofanoDto deleteQuirofano(Long id) throws Exception;
}
