package com.microservices.authorizationserver.mail;

import com.microservices.authorizationserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class ResetPasswordMail {



    private MessageSource messages;
    private JavaMailSender mailSender;

    @Autowired
    public ResetPasswordMail(JavaMailSender mailSender ,MessageSource messages){
        this.mailSender=mailSender;
        this.messages=messages;
    }




    public void  constructEmail(String contextPath,User user, Integer token) {
        String url = contextPath + "/user/changePassword?token=" + token;
        String  content=url+"\n\n" +"Best regards\n" +"Admin microservices" ;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Reset Password");
        email.setText("Hi "+user.getUsername()+"\n \n" +
                "Please use this code to reset your password : " +token+"\n" +
                "be careful this code has only 2 hour validity \n" +
                "\n"+

                "Best regards\n"+
                "Admin microservices"


        );
        email.setTo(user.getEmail());
        email.setFrom("admin@microservices.com");
        mailSender.send(email);
    }
}
