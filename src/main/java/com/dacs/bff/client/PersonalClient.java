package com.dacs.bff.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.dacs.bff.dto.PersonalDto;

@FeignClient(name = "backend", url = "${backend.base-url:http://localhost:8080}")
public interface PersonalClient {

    @GetMapping("/personal/{id}")
    PersonalDto.BackResponse getById(@PathVariable("id") Long id);

        @GetMapping("/personal")
        com.dacs.bff.dto.PaginacionDto.Response<PersonalDto.BackResponse> list(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "16") int size,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "role", required = false) String role
        );

        @PostMapping("/personal")
        PersonalDto.BackResponse create(@RequestBody PersonalDto.Create data);

        @PutMapping("/personal/{id}")
        PersonalDto.BackResponse update(@PathVariable("id") Long id, @RequestBody PersonalDto.Update data);

        @DeleteMapping("/personal/{id}")
        void delete(@PathVariable("id") Long id);

}
