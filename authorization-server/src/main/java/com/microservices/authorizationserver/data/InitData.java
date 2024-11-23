package com.microservices.authorizationserver.data;

import com.microservices.authorizationserver.model.Permission;
import com.microservices.authorizationserver.model.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * @apiNote  All  user  has permission to  create/read/delete/update his resume and his role  is User
 */
public  class InitData {

    public List<Role>  init(){
        List<Permission > permissions=new ArrayList<>() ;
        permissions.add( new Permission(1,"create")) ;
        permissions.add( new Permission(2,"read")) ;
        permissions.add( new Permission(3,"update")) ;
        permissions.add( new Permission(4,"delete")) ;
        Role role=new Role(1,"ROLE_user",permissions);
        List<Role>  roles=new ArrayList<>() ;
        roles.add(role) ;


      return  roles ;
    }
}
