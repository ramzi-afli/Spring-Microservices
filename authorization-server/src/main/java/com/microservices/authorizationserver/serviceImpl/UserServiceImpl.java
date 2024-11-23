package com.microservices.authorizationserver.serviceImpl;

import com.microservices.authorizationserver.dtos.PasswordDto;
import com.microservices.authorizationserver.exceptions.EmailAlreadyExistException;
import com.microservices.authorizationserver.exceptions.TokenExpiredException;
import com.microservices.authorizationserver.exceptions.TokenNotFoundException;
import com.microservices.authorizationserver.exceptions.UserNotFoundException;
import com.microservices.authorizationserver.model.PasswordResetToken;
import com.microservices.authorizationserver.model.User;
import com.microservices.authorizationserver.model.VerificationToken;
import com.microservices.authorizationserver.repository.RoleRepo;
import com.microservices.authorizationserver.repository.UserDetailRepository;
import com.microservices.authorizationserver.repository.VerificationTokenRepository;
import com.microservices.authorizationserver.repository.PasswordTokenRepository;
import com.microservices.authorizationserver.serviceImpl.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl  implements UserService {


    private  final UserDetailRepository userDetailRepository ;
    private  final PasswordEncoder encoder ;
    private final VerificationTokenRepository tokenRepository;
    private  final RoleRepo roleRepo ;
    private  final PasswordTokenRepository passwordTokenRepository ;

    @Autowired
    UserServiceImpl(UserDetailRepository userDetailRepository ,
                    PasswordEncoder passwordEncoder,
                    VerificationTokenRepository verificationTokenRepository,
                    RoleRepo roleRepo,
                    PasswordTokenRepository passwordTokenRepository
                    ){
        this.passwordTokenRepository=passwordTokenRepository ;
        this.userDetailRepository=userDetailRepository ;
        this.encoder=passwordEncoder ;
        this.tokenRepository=verificationTokenRepository;
        this.roleRepo=roleRepo;
    }


    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public VerificationToken createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        LocalDateTime now = LocalDateTime.now().plusDays(2)  ;
        myToken.setExpiryDate(now);
       return   tokenRepository.save(myToken);

    }


    @Override
    public void deleteUnregisteredToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Override
    public List<VerificationToken> findExpiredToken() {
        List<VerificationToken> allTokens = tokenRepository.findAll() ;
         return allTokens.stream().
                filter(verificationToken -> verificationToken.getExpiryDate().isBefore(LocalDateTime.now())  )
                 .collect(Collectors.toList());
    }

    @Override
    public User findUserByEmail(String userEmail) {
       Optional<User> optionalUser=  userDetailRepository.findByEmail(userEmail);
       if(optionalUser.isEmpty()){
           throw new UserNotFoundException("there is no user with email ="+userEmail) ;
       }
       return  optionalUser.get();
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userDetailRepository.save(user);
    }

    @Override
    public void deleteUnregisteredUser(String email) {
        userDetailRepository.deleteByEmail(email);
    }



    @Override
    public void createPasswordResetTokenForUser(User user, Integer token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user, LocalDateTime.now().plusHours(4) );
        passwordTokenRepository.save(myToken);

    }




    @Override
    public User createUser(User user) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setEnabled(false);
            user.setRoles(Arrays.asList(roleRepo.findByName("ROLE_user").get()));
            if(userDetailRepository.findByEmail(user.getEmail()).isPresent()){
                throw   new EmailAlreadyExistException(user.getEmail() +"Already exists") ;
            }
            userDetailRepository.save(user);
            return user ;

    }


    @Override
    public void deleteUserWithEmail(String email) {
        userDetailRepository.deleteByEmail(email) ;
    }









    @Override
    public Boolean validatePasswordResetToken(Integer token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
       if(!isTokenFound(passToken)){
           throw new TokenNotFoundException("please check the code in you email address ") ;
       }if(isTokenExpired(passToken)){
           throw   new TokenExpiredException("token is expired") ;
        }

         return  true ;

    }

    @Override
    public Boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    @Override
    public boolean isTokenExpired(PasswordResetToken passToken) {
        final LocalDateTime dateTime = LocalDateTime.now() ;
        return passToken.getExpiryDate().isBefore(dateTime);
    }





    @Override
    @Transactional
    public void  changePassword(PasswordDto passwordDto){
    final PasswordResetToken passToken = passwordTokenRepository.findByToken(passwordDto.getToken());
     User user=passToken.getUser() ;
     user.setPassword(encoder.encode(passwordDto.getNewPassword()));
     passwordTokenRepository.delete(passToken);

   }

   @Override
    public List<PasswordResetToken>  allExpiredResetCode(){
        return  passwordTokenRepository.findAll()
               .stream().
                filter(
                passwordResetToken ->  passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

}
