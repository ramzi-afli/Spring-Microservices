package com.microservices.authorizationserver.serviceImpl.services;

import com.microservices.authorizationserver.dtos.PasswordDto;
import com.microservices.authorizationserver.model.PasswordResetToken;
import com.microservices.authorizationserver.model.User;
import com.microservices.authorizationserver.model.VerificationToken;

import java.util.List;

public interface UserService {


    User createUser(User user) ;
    User getUser(String verificationToken);
    VerificationToken createVerificationToken(User user, String token);
    VerificationToken getVerificationToken(String VerificationToken);
    void saveRegisteredUser(User user) ;

    void deleteUnregisteredUser(String  email) ;

    void deleteUnregisteredToken(String token);
    List<VerificationToken> findExpiredToken()  ;
    User  findUserByEmail(String  userEmail) ;

    void createPasswordResetTokenForUser(User user, Integer token) ;

    Boolean validatePasswordResetToken(Integer token) ;
    Boolean isTokenFound(PasswordResetToken passToken) ;
    boolean isTokenExpired(PasswordResetToken passToken) ;
    void  changePassword(PasswordDto passwordDto) ;
    List<?>  allExpiredResetCode() ;
    void deleteUserWithEmail(String email) ;

}
