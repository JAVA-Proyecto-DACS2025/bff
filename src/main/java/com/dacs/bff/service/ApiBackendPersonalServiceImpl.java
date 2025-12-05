package com.dacs.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendPersonalClient;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.PersonalDto;

@Service
public class ApiBackendPersonalServiceImpl implements ApiBackendPersonalService {

    @Autowired
    private ApiBackendPersonalClient apiBackendPersonalClient;

    @Override
    public PaginatedResponse<PersonalDto.BackResponse> getPersonal(Integer page, Integer size) {
        return apiBackendPersonalClient.personales(page, size);
    }

    @Override
    public PersonalDto.BackResponse create(PersonalDto.Create personalRequestDto) {
        return apiBackendPersonalClient.create(personalRequestDto);
    }

    @Override
    public PersonalDto.BackResponse update(Long id, PersonalDto.Update personalRequestDto) {
        return apiBackendPersonalClient.update(id, personalRequestDto);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) throws Exception {
        return apiBackendPersonalClient.delete(id);

    }

    @Override
    public List<PersonalDto.Lite> searchByNombreOrDni(String param) {
        List<PersonalDto.BackResponse> backResponse = apiBackendPersonalClient.searchByNombreOrDni(param);
        return backResponse.stream()
                .map(pr -> {
                    PersonalDto.Lite lite = new PersonalDto.Lite();
                    lite.setId(pr.getId());
                    lite.setNombre(pr.getNombre());
                    lite.setDni(pr.getDni());
                    lite.setRol(pr.getRol());
                    lite.setLegajo(pr.getLegajo());
                    return lite;
                })
                .toList();
    }
}