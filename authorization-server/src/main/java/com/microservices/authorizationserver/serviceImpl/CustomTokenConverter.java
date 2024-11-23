package com.microservices.authorizationserver.serviceImpl;

import com.microservices.authorizationserver.model.User;
import com.microservices.authorizationserver.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ComponentScan
public class CustomTokenConverter extends JwtAccessTokenConverter {

    @Autowired
    private UserDetailRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken,final  OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        Map<String, Object> additionalInformation = new HashMap<>();
        if (authentication.getUserAuthentication() != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            additionalInformation.put("email", user.getEmail());
           // additionalInformation.put("user_id", user.getId());
        }
        token.setAdditionalInformation(additionalInformation);
        return accessToken;
    }
}