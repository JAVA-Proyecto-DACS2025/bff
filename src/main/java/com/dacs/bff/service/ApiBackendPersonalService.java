package com.dacs.bff.service;

import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.PersonalRequestDto;
import com.dacs.bff.dto.PersonalResponseDto;

public interface ApiBackendPersonalService {
    PaginatedResponse<PersonalResponseDto> getPersonal(Integer page, Integer size);

    PersonalResponseDto create(PersonalRequestDto personalRequestDto);

    PersonalResponseDto update(Long id,PersonalRequestDto personalRequestDto);

    void delete(Long id) throws Exception;
}
