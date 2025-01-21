package com.example.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends Exception {
    private final String code;
    private final String message;

    public CustomException(String message, String code) {
        this.code = code;
        this.message = message;
    }

}
