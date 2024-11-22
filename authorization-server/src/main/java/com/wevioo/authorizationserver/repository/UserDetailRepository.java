package com.microservices.authorizationserver.repository;

import com.microservices.authorizationserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<User,Integer> {


    Optional<User> findByUsername(String name);
    Optional<User> findByEmail(String email) ;
     @Transactional
     void  deleteByEmail(String email) ;


}
