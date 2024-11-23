package com.microservices.authorizationserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private LocalDateTime expiryDate;


   public  VerificationToken(
           String token,
           User user
   ){
        this.user=user ;
        this.token=token;
   }

    public  VerificationToken(
            String token,
            User user,
            LocalDateTime expiryDate
    ){
        this.user=user ;
        this.token=token;
        this.expiryDate=expiryDate;
    }

}