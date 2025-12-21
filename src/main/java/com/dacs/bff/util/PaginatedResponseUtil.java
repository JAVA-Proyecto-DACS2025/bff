package com.dacs.bff.util;

import com.dacs.bff.dto.PaginatedResponse;
import java.util.List;

public class PaginatedResponseUtil {
    public static <T, B> PaginatedResponse<T> build(PaginatedResponse<B> backend, List<T> content) {
        PaginatedResponse<T> response = new PaginatedResponse<>();
        response.setContent(content);
        response.setTotalElements(backend.getTotalElements());
        response.setTotalPages(backend.getTotalPages());
        response.setNumber(backend.getNumber());
        response.setSize(backend.getSize());
        return response;
    }
}
