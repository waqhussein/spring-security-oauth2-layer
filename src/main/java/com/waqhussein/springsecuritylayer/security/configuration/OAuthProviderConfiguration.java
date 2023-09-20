package com.waqhussein.springsecuritylayer.security.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth2")
public class OAuthProviderConfiguration {

    private Google google;

    @Getter
    @Setter
    public static class Google{
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String authorizationUri;
        private String tokenUri;
        private String userInfoUri;
        private String jwkSetUri;
        private String clientName;
        private String registrationId;
    }
}
