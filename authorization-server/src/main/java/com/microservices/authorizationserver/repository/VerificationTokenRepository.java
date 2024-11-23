package com.microservices.authorizationserver.repository;

import com.microservices.authorizationserver.model.User;
import com.microservices.authorizationserver.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    void deleteByToken(String token) ;

    VerificationToken findByUser(User user);



}