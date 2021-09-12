package com.jayden.client.support.error;

import lombok.Getter;

@Getter
public enum ErrorType {
    INTERNAL_SERVER_ERROR("I001", "Internal Server Error");

    private String code;
    private String message;

    ErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
