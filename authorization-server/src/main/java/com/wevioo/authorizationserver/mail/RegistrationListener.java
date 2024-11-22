package com.microservices.authorizationserver.mail;

import com.microservices.authorizationserver.config.OnRegistrationCompleteEvent;
import com.microservices.authorizationserver.model.User;
import com.microservices.authorizationserver.serviceImpl.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;




@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    private UserService service;
    private MessageSource messages;
    private JavaMailSender mailSender;


    @Autowired
    RegistrationListener(UserService service ,
                         MessageSource messages,
                         JavaMailSender mailSender
                         ){
        this.mailSender=mailSender;
        this.messages=messages;
        this.service=service;
    }





    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/regitrationConfirm?token=" + token;


        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setFrom("admin@microservices.com");
        email.setSubject("Activation Link");
        email.setText("Hi "+user.getUsername()+"\n \n" +
                "Please confirm you email  address  by clicking the  button bellow.\n" +
                        "This confirms that your email address is correct so  that we can use  it to help you\n" +
                        "recover your password in the future \n" +
                        "\n"+
                "http://localhost:9091/user" + confirmationUrl+"\n \n"+
                "Best regards\n"+
                "Admin microservices"


        );
        mailSender.send(email);
    }
}