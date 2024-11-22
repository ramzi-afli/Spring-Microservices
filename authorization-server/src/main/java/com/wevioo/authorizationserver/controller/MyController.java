package com.microservices.authorizationserver.controller;


import com.microservices.authorizationserver.model.AuthUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MyController{


    @RequestMapping("/mapping")
    public String myMethod(ModelMap model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetail userDetails = (AuthUserDetail)auth.getPrincipal();
        model.addAttribute("email", userDetails.getEmail());
        //model.addAttribute("lastName", userDetails.getLastName());
        return " ";
    }
}