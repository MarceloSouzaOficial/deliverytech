package com.deliverytech.delivery_api.exceptions;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    int status, 
    String message, 
    long timestamp, 
    List<ValidationError> errors) {

        public record ValidationError(
            String field,
            String message
        ) {}

}