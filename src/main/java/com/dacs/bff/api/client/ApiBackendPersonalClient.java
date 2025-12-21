package com.dacs.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.PersonalDto;

@FeignClient(name = "apiBackendPersonalClient", url = "${feign.client.config.apiBackendPersonalClient.url}", configuration = FeignConfig.class)

public interface ApiBackendPersonalClient {

    // @GetMapping("/personal")
    // PaginatedResponse<PersonalDto.BackResponse> getPersonal(@RequestParam(name = "page", required = false) Integer page,
    //         @RequestParam(name = "size", required = false) Integer size);

    @PostMapping("/personal")
    ResponseEntity<PersonalDto.BackResponse> create(@RequestBody PersonalDto.Create personal);

    @PutMapping("/personal/{id}")
    ResponseEntity<PersonalDto.BackResponse> update(@PathVariable("id") Long id, @RequestBody PersonalDto.Update personal);

    @DeleteMapping("/personal/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);

    @GetMapping("/personal")
    PaginatedResponse<PersonalDto.BackResponse> getPersonal(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "param", required = false) String param);

}