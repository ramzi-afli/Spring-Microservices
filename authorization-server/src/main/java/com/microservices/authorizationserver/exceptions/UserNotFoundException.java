package com.microservices.authorizationserver.exceptions;




public class UserNotFoundException extends  RuntimeException{

    private  String msg ;
    public UserNotFoundException(String message) {
        super(message);
    }
}
