package com.dacs.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.PersonalRequestDto;
import com.dacs.bff.dto.PersonalResponseDto;

@FeignClient(name = "apiBackendPersonalClient", url = "${feign.client.config.apiBackendPersonalClient.url}", configuration = FeignConfig.class)

public interface ApiBackendPersonalClient {

    @GetMapping("/personal")
    PaginatedResponse<PersonalResponseDto> personales(@RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size);

    @PostMapping("/personal")
    PersonalResponseDto create(@RequestBody PersonalRequestDto personal);

    @PutMapping("/personal/{id}")
    PersonalResponseDto update(@PathVariable("id") Long id, @RequestBody PersonalRequestDto personal);

    @DeleteMapping("/personal/{id}")
    void delete(@PathVariable("id") Long id);


}
