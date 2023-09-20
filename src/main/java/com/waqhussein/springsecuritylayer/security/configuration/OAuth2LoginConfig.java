package com.waqhussein.springsecuritylayer.security.configuration;

import com.waqhussein.springsecuritylayer.facade.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(value = {OAuthProviderConfiguration.class})
public class OAuth2LoginConfig {

    @Autowired
    private UserDetailsImpl userDetailsImpl;

    @Autowired
    OAuthProviderConfiguration oAuthProviderConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> {
                oauth2.clientRegistrationRepository(this.clientRegistrationRepository());
                oauth2.userInfoEndpoint(userInfo -> {
                    userInfo.oidcUserService(this.oidcUserService());
                });
            });
        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }


    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
            userDetailsImpl.saveLoggedInUser(oidcUser);
            return oidcUser;
        };
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId(oAuthProviderConfiguration.getGoogle().getRegistrationId())
                .clientId(oAuthProviderConfiguration.getGoogle().getClientId())
                .clientSecret(oAuthProviderConfiguration.getGoogle().getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(oAuthProviderConfiguration.getGoogle().getRedirectUri())
                .scope("openid", "profile", "email")
                .authorizationUri(oAuthProviderConfiguration.getGoogle().getAuthorizationUri())
                .tokenUri(oAuthProviderConfiguration.getGoogle().getTokenUri())
                .userInfoUri(oAuthProviderConfiguration.getGoogle().getUserInfoUri())
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri(oAuthProviderConfiguration.getGoogle().getJwkSetUri())
                .clientName(oAuthProviderConfiguration.getGoogle().getClientName())
                .build();
    }

    @Bean
    public JwtDecoderFactory<ClientRegistration> idTokenDecoderFactory() {
        OidcIdTokenDecoderFactory idTokenDecoderFactory = new OidcIdTokenDecoderFactory();
        idTokenDecoderFactory.setJwsAlgorithmResolver(clientRegistration -> SignatureAlgorithm.RS256);
        return idTokenDecoderFactory;
    }

}