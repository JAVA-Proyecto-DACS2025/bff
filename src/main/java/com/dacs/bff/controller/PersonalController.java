package com.dacs.bff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.PaginatedResponse;
import com.dacs.bff.dto.PersonalDto;
import com.dacs.bff.service.ApiBackendPersonalService;
import com.dacs.bff.util.ApiResponseBuilder;

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
    public ResponseEntity<ApiResponse<PaginatedResponse<PersonalDto.BackResponse>>> getPersonal(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "param", required = false) String param) throws Exception {
        PaginatedResponse<PersonalDto.BackResponse> backend = personalService.getPersonal(page, size, param);
        return ApiResponseBuilder.okWithPagination(backend);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<PersonalDto.BackResponse>> createPersonal(
            @RequestBody PersonalDto.Create personalRequestDto) throws Exception { /// ARREGLAR EL MAPEADO
        PersonalDto.BackResponse entity = personalService.create(personalRequestDto);

        ApiResponse<PersonalDto.BackResponse> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setData(entity);
        resp.setMessage(null);
        resp.setTimestamp(java.time.OffsetDateTime.now().toString());
        resp.setRequestId(java.util.UUID.randomUUID().toString());

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PersonalDto.BackResponse>> updatePersonal(@PathVariable Long id,
            @RequestBody PersonalDto.Update personalRequestDto) throws Exception {
        PersonalDto.BackResponse entity = personalService.update(id, personalRequestDto);
        return ApiResponseBuilder.ok(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePersonal(@PathVariable Long id) throws Exception {
        ResponseEntity<Void> backResponse = personalService.delete(id);
        if (backResponse.getStatusCode().is2xxSuccessful()) {
            return ApiResponseBuilder.ok(null, "Personal eliminado exitosamente");
        } else {
            return ApiResponseBuilder.serverError("No se pudo eliminar el personal");
        }
    }

    @GetMapping("/resumen")
    public ResponseEntity<ApiResponse<PaginatedResponse<PersonalDto.FrontResponseLite>>> getPersonalLite(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "param", required = false) String param) {
        PaginatedResponse<PersonalDto.FrontResponseLite> results = personalService.getPersonalLite(page, size, param);
        return ApiResponseBuilder.okWithPagination(results);
    }

}
