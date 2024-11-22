package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * Main app
 */

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class   microservicesApplication {
	public static void main(String[] args) {
		SpringApplication.run(microservicesApplication.class, args);
	}

}
