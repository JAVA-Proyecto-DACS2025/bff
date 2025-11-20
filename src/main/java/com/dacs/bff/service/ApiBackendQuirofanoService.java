package com.dacs.bff.service;

import com.dacs.bff.dto.QuirofanoDTO;

import java.util.List;

public interface ApiBackendQuirofanoService {
    public List<QuirofanoDTO> getQuirofanos();
    
    public QuirofanoDTO saveQuirofano(QuirofanoDTO quirofano) throws Exception;

    public QuirofanoDTO updateQuirofano(QuirofanoDTO quirofano) throws Exception;
    
    public QuirofanoDTO deleteQuirofano(Long id) throws Exception;
}
