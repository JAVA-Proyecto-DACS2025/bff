package com.dacs.bff.dto;

import java.util.List;

import lombok.Data;

@Data
public class PaginatedResponse<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int number;
    private int size;
}

