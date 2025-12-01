package com.dacs.bff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.Pagination;
import com.dacs.bff.dto.PersonalRequestDto;
import com.dacs.bff.dto.PersonalResponseDto;
import com.dacs.bff.service.ApiBackendPersonalService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@RequestMapping("/personal")
public class PersonalController {

    @Autowired
    private ApiBackendPersonalService personalService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<PersonalResponseDto>>> getPersonal(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {
        PaginatedResponse<PersonalResponseDto> backend = personalService.getPersonal(page, size);

        ApiResponse<List<PersonalResponseDto>> resp = new ApiResponse<>(); // MOVER a servicio o helper
        resp.setSuccess(true);
        resp.setData(backend.getContent());
        resp.setMessage(null);
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());

        Pagination p = new Pagination();
        p.setPage(backend.getNumber());
        p.setPageSize(backend.getSize());
        p.setTotalItems(backend.getTotalElements());
        p.setTotalPages(backend.getTotalPages());
        p.setHasNext(backend.getNumber() < backend.getTotalPages() - 1);
        p.setHasPrevious(backend.getNumber() > 0);
        resp.setPagination(p);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<PersonalResponseDto>> createPersonal(
            @RequestBody PersonalRequestDto personalRequestDto) throws Exception { /// ARREGLAR EL MAPEADO
        PersonalResponseDto entity = personalService.create(personalRequestDto);

        ApiResponse<PersonalResponseDto> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setData(entity);
        resp.setMessage(null);
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PersonalResponseDto>> updatePersonal(@PathVariable Long id,
            @RequestBody PersonalRequestDto personalRequestDto) throws Exception {
        PersonalResponseDto entity = personalService.update(id, personalRequestDto);

        ApiResponse<PersonalResponseDto> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setData(entity);
        resp.setMessage(null);
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());

        return new ResponseEntity<>(resp, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePersonal(@PathVariable Long id) throws Exception {
        personalService.delete(id);
        ApiResponse<Void> resp = new ApiResponse<Void>();
        resp.setSuccess(true);
        resp.setData(null);
        resp.setMessage(null);
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
