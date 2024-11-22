package com.microservices.authorizationserver.repository;

import com.microservices.authorizationserver.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken ,Long> {

    PasswordResetToken  findByToken( Integer  token)  ;
}
