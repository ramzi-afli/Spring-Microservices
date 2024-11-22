package com.microservices.authorizationserver.exceptions;

public class TokenExpiredException extends  RuntimeException{
    public  TokenExpiredException(String message){
        super(message);
    }
}
