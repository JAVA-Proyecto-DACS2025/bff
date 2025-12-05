package com.dacs.bff.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.PersonalDto;

public interface ApiBackendPersonalService {
    PaginatedResponse<PersonalDto.BackResponse> getPersonal(Integer page, Integer size);

    PersonalDto.BackResponse create(PersonalDto.Create personalRequestDto);

    PersonalDto.BackResponse update(Long id,PersonalDto.Update personalRequestDto);

    ResponseEntity<Void> delete(Long id) throws Exception;

    List<PersonalDto.Lite> searchByNombreOrDni(String param);
}
