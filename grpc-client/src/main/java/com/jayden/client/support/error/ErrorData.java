package com.jayden.client.support.error;

import lombok.Getter;

@Getter
public class ErrorData {
    private String code;
    private String message;

    public ErrorData(ErrorType errorType) {
        this.code = errorType.getCode();
        this.message = errorType.getMessage();
    }
}
