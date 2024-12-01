package com.onboarding.demo.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundRestException extends RestException {

    public NotFoundRestException(String message, String detail) {
        super(HttpStatus.NOT_FOUND, message, detail);
    }
}
