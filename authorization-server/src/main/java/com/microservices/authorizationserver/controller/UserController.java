package com.microservices.authorizationserver.controller;


import com.microservices.authorizationserver.config.OnRegistrationCompleteEvent;
import com.microservices.authorizationserver.dtos.PasswordDto;
import com.microservices.authorizationserver.dtos.UserDto;
import com.microservices.authorizationserver.exceptions.EmailAlreadyExistException;
import com.microservices.authorizationserver.mail.ResetPasswordMail;
import com.microservices.authorizationserver.model.User;
import com.microservices.authorizationserver.model.VerificationToken;
import com.microservices.authorizationserver.serviceImpl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.time.LocalDateTime;


/**
 * @author Afli  Ramzi
 */



@RestController
@RequestMapping("/user")

public class UserController {


    private ApplicationEventPublisher eventPublisher ;
    private  final UserServiceImpl userService ;
    private ModelMapper  mapper ;
    private ResetPasswordMail resetPasswordMail ;


    @Autowired
     UserController(UserServiceImpl services,
                    ModelMapper modelMapper,
                    ApplicationEventPublisher eventPublisher,
                    ResetPasswordMail resetPasswordMail
    ){

         this.userService=services;
         this.mapper=modelMapper;
         this.eventPublisher=eventPublisher;
         this.resetPasswordMail=resetPasswordMail;
    }

    /**
     *
     * @param userDto
     * @param request,UserDto
     * @return ResponseEntity
     *@exception EmailAlreadyExistException
     */


    @ApiOperation(value = "create account ",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDto userDto ,
                                         HttpServletRequest request
                                         ) {

             User user = mapper.map(userDto, User.class);
             try {

                 User user1=userService.createUser(user) ;
                 String appUrl = request.getContextPath();
                 eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user,
                         request.getLocale(), appUrl));

                 return new ResponseEntity<>(user1, HttpStatus.CREATED);

             }catch (EmailAlreadyExistException exception){
                  return new ResponseEntity<>(user.getEmail() +" Already exists",HttpStatus.BAD_REQUEST) ;
             }
     }



      /**
     *
     * @param request
     * @param model
     * @param token(validation code )
       * @apiNote   we gonna check at first if the token is valid or not.
       * then if it is expired  the user  && validation token must be deleted form the db
       * else (token not expired and valid ) we  user is enabled==true (user can access his account)
     * @return message


     */


      @ApiOperation(value = "confirm registration ", response = Iterable.class)
      @ApiResponses(value = {
              @ApiResponse(code = 200, message = "Success|OK"),
              @ApiResponse(code = 401, message = "not authorized!"),
              @ApiResponse(code = 403, message = "forbidden!!!"),
              @ApiResponse(code = 404, message = "not found!!!") })

         @GetMapping("/regitrationConfirm")
         public String confirmRegistration (WebRequest request, Model model, @RequestParam("token") String token) {
             VerificationToken verificationToken = userService.getVerificationToken(token);
             User user = verificationToken.getUser();
             LocalDateTime checkValidationDate = LocalDateTime.now();
             if(verificationToken.getExpiryDate().isAfter(checkValidationDate)) {
                  user.setEnabled(true);
                 userService.saveRegisteredUser(user);
                 return "Account is successfully activated";
             }else{

                      userService.deleteUnregisteredUser(user.getEmail());
                      userService.deleteUnregisteredToken(verificationToken.getToken());
                     return  "Token is expired . Please create a new Account" ;
                 }


         }





    @ApiOperation(value = "reset password ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })

    /**
     * @params request ,userEmail
     * @apiNote  reset password
     */
    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        SecureRandom secureRandom = new SecureRandom();
        Integer randomWithSecureRandom = secureRandom.nextInt();
        userService.createPasswordResetTokenForUser(user,randomWithSecureRandom);
        resetPasswordMail.constructEmail(request.getContextPath(),user,randomWithSecureRandom);
        return  new ResponseEntity<>(HttpStatus.OK) ;

    }



    @ApiOperation(value = "change password ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })


    @PostMapping("/changePassword")
    public ResponseEntity ChangePassword(@RequestBody PasswordDto dto ) {
       userService.validatePasswordResetToken(dto.getToken());
       userService.changePassword(dto) ;
       return  new ResponseEntity<>(HttpStatus.OK) ;
    }


    @ApiOperation(value = "delete user ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })


    @DeleteMapping("/{email}")
    @PreAuthorize("hasAuthority('delete_user')")
    public  ResponseEntity<Void> deleteUser(@PathVariable("email") String userEmail ){
        userService.deleteUserWithEmail(userEmail);
        return  new ResponseEntity<>(HttpStatus.OK) ;
    }


}






