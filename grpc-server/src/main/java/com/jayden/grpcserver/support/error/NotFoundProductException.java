package com.jayden.grpcserver.support.error;

public class NotFoundProductException extends RuntimeException {

    public NotFoundProductException(String message) {
        super(message);
    }
}
