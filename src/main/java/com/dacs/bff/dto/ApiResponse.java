package com.dacs.bff.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private String timestamp;
    private String requestId;

}
