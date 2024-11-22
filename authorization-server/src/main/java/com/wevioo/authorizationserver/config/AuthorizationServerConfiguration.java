package com.microservices.authorizationserver.config;

import com.microservices.authorizationserver.serviceImpl.CustomTokenConverter;
import com.microservices.authorizationserver.serviceImpl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;


/**
 *
 * @author  Afli Ramzi
 * @apiNote  Authorization server configuration
 *
 */

@Configuration
public class AuthorizationServerConfiguration implements AuthorizationServerConfigurer {

    private final PasswordEncoder passwordEncoder;
    private final  DataSource dataSource;
    private final  AuthenticationManager authenticationManager;
    private  final UserDetailServiceImpl userDetailService  ;
    
    @Autowired
    AuthorizationServerConfiguration(PasswordEncoder passwordEncoder,
                                     DataSource dataSource,
                                     AuthenticationManager authenticationManager,
                                     UserDetailServiceImpl userDetailService
                                     ){

       this.authenticationManager=authenticationManager ;
       this.dataSource=dataSource ;
       this.passwordEncoder=passwordEncoder ;
       this.userDetailService=userDetailService ;
    }

    @Bean
    TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);

    }



    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jdbcTokenStore());
        endpoints.tokenEnhancer(customTokenEnhancer());
        endpoints.authenticationManager(authenticationManager);
        endpoints.userDetailsService(userDetailService);
        //endpoints.tokenEnhancer(customTokenEnhancer());

    }


    @Bean
    public CustomTokenConverter customTokenEnhancer() {
        return new CustomTokenConverter();
    }
}
