package com.dacs.bff.util;

import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.PaginatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class ApiResponseBuilder {

    public static <T> ResponseEntity<ApiResponse<T>> buildResponse(T data, HttpStatus status, String message) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.setSuccess(status.is2xxSuccessful());
        resp.setData(data);
        resp.setMessage(message);
        resp.setTimestamp(OffsetDateTime.now().toString());
        resp.setRequestId(UUID.randomUUID().toString());
        return new ResponseEntity<>(resp, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return buildResponse(data, HttpStatus.OK, null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        return buildResponse(data, HttpStatus.OK, message);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return buildResponse(data, HttpStatus.CREATED, message);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        return buildResponse(null, HttpStatus.BAD_REQUEST, message);
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return buildResponse(null, HttpStatus.NOT_FOUND, message);
    }

    public static <T> ResponseEntity<ApiResponse<T>> serverError(String message) {
        return buildResponse(null, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static <T> ResponseEntity<ApiResponse<List<T>>> okWithPagination(PaginatedResponse<T> pageResp) {
        ApiResponse<List<T>> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setData(pageResp.getContent());
        resp.setMessage(null);
        resp.setTimestamp(OffsetDateTime.now().toString());
        resp.setRequestId(UUID.randomUUID().toString());

        // llenar metadatos de paginación
        var pagination = new com.dacs.bff.dto.Pagination();
        pagination.setPage(pageResp.getNumber());
        pagination.setPageSize(pageResp.getSize());
        pagination.setTotalItems(pageResp.getTotalElements());
        pagination.setTotalPages(pageResp.getTotalPages());
        resp.setPagination(pagination);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
