package com.waqhussein.springsecuritylayer.facade;

import com.waqhussein.springsecuritylayer.dto.UserProfile;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.security.Principal;

public interface IUserDetails {
    void saveLoggedInUser(OidcUser oidcUser);
    UserProfile getAuthenticatedUserProfile(Principal principal);
}
