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
    public PaginatedResponse<PersonalDto.BackResponse> getPersonal(Integer page, Integer size, String param) throws Exception {
        return apiBackendPersonalClient.getPersonal(page, size, param);
    }

    @Override
    public ResponseEntity<PersonalDto.BackResponse> create(PersonalDto.Create personalRequestDto) {
        return apiBackendPersonalClient.create(personalRequestDto);
    }

    @Override
    public ResponseEntity<PersonalDto.BackResponse> update(Long id, PersonalDto.Update personalRequestDto) {
        return apiBackendPersonalClient.update(id, personalRequestDto);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) throws Exception {
        return apiBackendPersonalClient.delete(id);

    }

    @Override
    public PaginatedResponse<PersonalDto.FrontResponseLite> getPersonalLite(Integer page, Integer size, String param) {
        PaginatedResponse<PersonalDto.BackResponse> backResp = apiBackendPersonalClient.getPersonal(page, size, param);
        List<PersonalDto.FrontResponseLite> mappedContent = backResp.getContent().stream()
                .map(back -> {
                    PersonalDto.FrontResponseLite front = new PersonalDto.FrontResponseLite();
                    front.setId(back.getId());
                    front.setDni(back.getDni());
                    front.setLegajo(back.getLegajo());
                    front.setNombre(back.getNombre());
                    front.setRol(back.getRol());
                    return front;
                })
                .toList();
        return com.dacs.bff.util.PaginatedResponseUtil.build(backResp, mappedContent);
    }
}