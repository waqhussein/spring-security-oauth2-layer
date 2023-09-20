package com.waqhussein.springsecuritylayer.facade.impl;

import com.waqhussein.springsecuritylayer.dto.UserProfile;
import com.waqhussein.springsecuritylayer.entity.UserDetailsEntity;
import com.waqhussein.springsecuritylayer.facade.IUserDetails;
import com.waqhussein.springsecuritylayer.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class UserDetailsImpl implements IUserDetails {

    @Autowired
    private UserDetailsService userDetailsService;

    public void saveLoggedInUser(OidcUser oidcUser){
        UserDetailsEntity userDetailsEntity = userDetailsService.getUser(oidcUser.getEmail());
        if(userDetailsEntity == null){
            userDetailsEntity = new UserDetailsEntity();
            userDetailsEntity.setEmailAddress(oidcUser.getEmail());
            userDetailsEntity.setFirstName(oidcUser.getGivenName());
            userDetailsEntity.setLastName(oidcUser.getFamilyName());
            userDetailsEntity.setCreatedAt(Timestamp.from(Instant.now()));
        }
        userDetailsEntity.setLastLoginTime(Timestamp.from(Instant.now()));
        userDetailsService.saveUser(userDetailsEntity);
    }

    public UserProfile getAuthenticatedUserProfile(Principal principal){
        OAuth2User oAuth2User = ((OAuth2AuthenticationToken) principal).getPrincipal();
        UserProfile userProfile = new UserProfile();
        userProfile.setFullName(oAuth2User.getAttribute("name"));
        userProfile.setEmailAddress(oAuth2User.getAttribute("email"));
        userProfile.setPictureUrl(oAuth2User.getAttribute("picture"));
        return userProfile;
    }
}
