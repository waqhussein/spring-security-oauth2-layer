package com.waqhussein.springsecuritylayer.controller;

import com.waqhussein.springsecuritylayer.dto.UserProfile;
import com.waqhussein.springsecuritylayer.facade.IUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserDetails userDetails;

    @GetMapping("/profile")
    public UserProfile getUserDetails(Principal principal){
        return userDetails.getAuthenticatedUserProfile(principal);
    }
}
