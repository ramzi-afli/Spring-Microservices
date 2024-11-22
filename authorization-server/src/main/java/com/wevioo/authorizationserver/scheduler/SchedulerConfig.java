package com.microservices.authorizationserver.scheduler;


import com.microservices.authorizationserver.model.PasswordResetToken;
import com.microservices.authorizationserver.model.User;
import com.microservices.authorizationserver.repository.VerificationTokenRepository;
import com.microservices.authorizationserver.repository.PasswordTokenRepository;
import com.microservices.authorizationserver.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


/**
 * @author  Afli Ramzi
 *
 */
@Component
@EnableScheduling
public class SchedulerConfig {


private  final UserServiceImpl userService ;
private  final VerificationTokenRepository tokenRepository ;

private  final PasswordTokenRepository passwordTokenRepository ;

@Autowired
  public    SchedulerConfig(UserServiceImpl userService ,
                            VerificationTokenRepository tokenRepository,
                            PasswordTokenRepository passwordTokenRepository
                            )

    {
        this.tokenRepository=tokenRepository ;
        this.userService=userService;
        this.passwordTokenRepository=passwordTokenRepository ;
    }


    /**
     * @apiNote  every day at 03 pm a scheduler must delete all  user with expired validation token
     * to prevent  from DOS
     *
     */


@Scheduled(cron =  "${cron.value}",zone = "Europe/Paris")

   public  void deleteUnconfirmedUserWithExpiredDate(){
           // all  expired  code   from the  db
       var allExpiredToken= userService.findExpiredToken() ;
        //for every one of those  we  gonna  take that code and  check if his user is enabled
        //if enabled  ==true we delete only the  token  else  we  delete bouth user  and  the token
      allExpiredToken.forEach(verificationToken -> {
              String token  =verificationToken.getToken() ;
              User user=userService.getUser(token ) ;
              if(user.isEnabled()==true){
                  tokenRepository.delete(verificationToken);
              }else {
              tokenRepository.delete(verificationToken);
             userService.deleteUnregisteredUser(user.getEmail());
             }
         });
      //deleting all reset password  code
         var allExpiredResetCode=userService.allExpiredResetCode();
         passwordTokenRepository.deleteAll(allExpiredResetCode);
     }





}
