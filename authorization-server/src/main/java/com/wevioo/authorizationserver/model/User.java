package com.microservices.authorizationserver.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.microservices.authorizationserver.utility.EmailPattern.EMAIL_PATTERN;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {
    public User() {
    }

    public User(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.accountNonExpired = user.isAccountNonExpired();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.roles = user.getRoles();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    @NotNull(message = "username must be present")
    private String username;
    @Column(name = "password")
    @NotNull(message = "password must be present")
    @Basic (fetch = FetchType.LAZY)
    private String password;
    @Column(name = "email")
    @Email(regexp = EMAIL_PATTERN)
    private String email;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "accountNonExpired")
    private boolean accountNonExpired=true;
    @Column(name = "credentialsNonExpired")
    private boolean credentialsNonExpired=true;
    @Column(name = "accountNonLocked")
    private boolean accountNonLocked=true;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles=new ArrayList<>() ;





}
