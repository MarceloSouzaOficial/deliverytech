package com.deliverytech.delivery_api.exceptions;

public class BusinessException extends RuntimeException{
    public BusinessException(String mensagem){
        super(mensagem);
    }
} 