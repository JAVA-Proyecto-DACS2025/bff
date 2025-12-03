package com.dacs.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendPersonalClient;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.PersonalDto;
import com.dacs.bff.dto.PersonalRequestDto;
import com.dacs.bff.dto.PersonalResponseDto;

@Service
public class ApiBackendPersonalServiceImpl implements ApiBackendPersonalService {

    @Autowired
    private ApiBackendPersonalClient apiBackendPersonalClient;

    @Override
    public PaginatedResponse<PersonalResponseDto> getPersonal(Integer page, Integer size) {
        return apiBackendPersonalClient.personales(page, size);
    }

    @Override
    public PersonalResponseDto create(PersonalRequestDto personalRequestDto) {
        return apiBackendPersonalClient.create(personalRequestDto);
    }

    @Override
    public PersonalResponseDto update(Long id, PersonalRequestDto personalRequestDto) {
        return apiBackendPersonalClient.update(id, personalRequestDto);
    }

    @Override
    public void delete(Long id) throws Exception {
        apiBackendPersonalClient.delete(id);

    }

    @Override
    public List<PersonalDto> searchByNombreOrDni(String param) {
        return apiBackendPersonalClient.searchByNombreOrDni(param);
    }
}