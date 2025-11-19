package com.dacs.bff.service;

import java.util.List;

import com.dacs.bff.dto.CirugiaDTO;

public interface ApiBackendCirugiaService {

    public List<CirugiaDTO> getCirugias();

    public CirugiaDTO saveCirugia(CirugiaDTO cirugia) throws Exception;

    public CirugiaDTO updateCirugia(CirugiaDTO cirugia) throws Exception;

    public CirugiaDTO deleteCirugia(Long id) throws Exception;
}