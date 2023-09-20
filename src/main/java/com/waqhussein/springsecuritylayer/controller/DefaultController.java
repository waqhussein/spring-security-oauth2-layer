package com.waqhussein.springsecuritylayer.controller;

import com.waqhussein.springsecuritylayer.dto.UserProfile;
import com.waqhussein.springsecuritylayer.facade.IUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class DefaultController {

    @Autowired
    private IUserDetails userDetails;

    @GetMapping()
    public UserProfile getUserDetails(Principal principal){
        return userDetails.getAuthenticatedUserProfile(principal);
    }

}
