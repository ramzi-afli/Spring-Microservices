package com.microservices.authorizationserver.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "password_reset_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
   private LocalDateTime expiryDate;

    public PasswordResetToken(Integer token, User user, LocalDateTime time) {
        this.token=token ;
        this.user=user ;
        this.expiryDate=time ;
    }


}
