package com.microservices.authorizationserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PasswordDto {
    private  Integer token ;
    private String newPassword;
}