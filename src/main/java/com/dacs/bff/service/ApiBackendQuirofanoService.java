package com.dacs.bff.service;

import com.dacs.bff.dto.QuirofanoDto;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ApiBackendQuirofanoService {
    public ResponseEntity<List<QuirofanoDto>> getQuirofanos();
    
    public ResponseEntity<QuirofanoDto> saveQuirofano(QuirofanoDto quirofano) throws Exception;

    public ResponseEntity<QuirofanoDto> updateQuirofano(QuirofanoDto quirofano) throws Exception;
    
    public ResponseEntity<QuirofanoDto> deleteQuirofano(Long id) throws Exception;
}
