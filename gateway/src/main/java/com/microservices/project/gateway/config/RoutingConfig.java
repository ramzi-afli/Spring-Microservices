package com.microservices.project.gateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class RoutingConfig {

    @Bean
    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("cv-server-id", r->r.path("/cv/**").uri("lb://CV-SERVICE")) //dynamic routing to cv-service
                .route("oauth-server-id", r->r.path("/oauth/**").uri("lb://AUTHORIZATION-SERVICE")) //dynamic routing to cv-service
                .route("oauth-server-id", r->r.path("/user/register/**").uri("lb://AUTHORIZATION-SERVICE")) //dynamic routing to cv-service
                .route("oauth-server-id", r->r.path("/user/regitrationConfirm/**").uri("lb://AUTHORIZATION-SERVICE")) //dynamic routing to cv-service

                .build();
    }
}
