package com.microservices.authorizationserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import java.time.LocalDateTime;

/**
 *
 * MAIN APP
 */

@PropertySources({
        @PropertySource(value = "classpath:application.properties"),
})
@SpringBootApplication
@EnableAuthorizationServer
@EnableEurekaClient
public class  AuthorizationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args) ;
    }

}
