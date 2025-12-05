package com.dacs.bff.dto;

import lombok.Data;

@Data
public class Pagination {
    private int page;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
