package com.dacs.bff.service;

import java.util.List;

import com.dacs.bff.dto.CirugiaDTO;

public interface ApiBackendCirugiaService {

    public com.dacs.bff.dto.CirugiaPageResponse getCirugias(Integer page, Integer size);

    public CirugiaDTO saveCirugia(CirugiaDTO cirugia) throws Exception;

    public CirugiaDTO updateCirugia(CirugiaDTO cirugia) throws Exception;

    public CirugiaDTO deleteCirugia(Long id) throws Exception;
}