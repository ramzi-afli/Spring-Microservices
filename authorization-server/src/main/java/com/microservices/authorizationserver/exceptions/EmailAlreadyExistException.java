package com.microservices.authorizationserver.exceptions;



public class EmailAlreadyExistException  extends  RuntimeException{

    public EmailAlreadyExistException(String msg ){
        super(msg);
    }
}
