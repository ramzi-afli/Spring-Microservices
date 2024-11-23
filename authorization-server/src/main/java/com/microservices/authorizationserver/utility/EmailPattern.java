package com.microservices.authorizationserver.utility;


import org.springframework.stereotype.Component;


@Component

public class EmailPattern {
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@microservices.com$";

}
