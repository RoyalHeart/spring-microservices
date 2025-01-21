package com.example.core.api.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class ErrorDetail {
    private String message;
    @JsonInclude(value = Include.NON_EMPTY)
    private String code;
}
