package com.microservices.authorizationserver.config;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


/**
 * @apiNote  All   Bean  instantiation are her
 */

@Configuration
@ComponentScan
public class BeansConfig {

    private  final EmailConfig config ;

    @Autowired

    BeansConfig(EmailConfig config){
        this.config=config;
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
   JavaMailSender mailSender(){

        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl() ;
        javaMailSender.setHost(config.getHost());
        javaMailSender.setPort(config.getPort());
        javaMailSender.setUsername(config.getUsername());
        javaMailSender.setPassword(config.getPassword());
    return  javaMailSender ;
 }

}
