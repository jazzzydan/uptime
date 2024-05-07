package com.uptime.lolov4back.util;

import lombok.Data;

@Data
public class ApiError {
    private String message;
    private Integer errorCode;
}
