package com.foomoo.awf.oauth2;

import com.foomoo.awf.onedrive.SingleTokenClientTokenServices;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.List;

import static java.util.Collections.singletonList;

public class OAuth2RestTemplateFactory {

    private static final int PERSISTED_TOKEN_ID = 1;

    private final ClientTokenServices clientTokenServices;
    private final AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

    public OAuth2RestTemplateFactory(final AccessTokenRepository accessTokenRepository,
                                     final OAuth2ProtectedResourceDetails details) {

        this.clientTokenServices = new SingleTokenClientTokenServices(PERSISTED_TOKEN_ID, accessTokenRepository);
        this.authorizationCodeResourceDetails = (AuthorizationCodeResourceDetails) details;
    }

    public OAuth2RestTemplate createOAuth2RestTemplateFromTemplate(final OAuth2RestTemplate oAuth2RestTemplate) {
        clientTokenServices.saveAccessToken(null, null, oAuth2RestTemplate.getAccessToken());
        return createOAuth2RestTemplate();
    }

    public OAuth2RestTemplate createOAuth2RestTemplate() {
        final ClientOnlyAuthorisationCodeResourceDetails wrappedDetails =
                new ClientOnlyAuthorisationCodeResourceDetails(authorizationCodeResourceDetails);

        final OAuth2AccessToken accessToken = clientTokenServices.getAccessToken(null, null);
        if (accessToken == null) {
            // No access token, don't create the template.
            return null;
        }

        final OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(wrappedDetails, new DefaultOAuth2ClientContext(accessToken));

        final AccessTokenProviderChain provider = new AccessTokenProviderChain(singletonList(new AuthorizationCodeAccessTokenProvider()));
        provider.setClientTokenServices(clientTokenServices);

        oAuth2RestTemplate.setAccessTokenProvider(provider);
        return oAuth2RestTemplate;
    }

    /**
     * Wrap an {@link org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails} object to report it as being client only. This
     * allows credentials to be cached and renewed without the user being present.
     */
    private static class ClientOnlyAuthorisationCodeResourceDetails extends AuthorizationCodeResourceDetails {

        private final AuthorizationCodeResourceDetails delegate;

        public ClientOnlyAuthorisationCodeResourceDetails(final AuthorizationCodeResourceDetails delegate) {
            this.delegate = delegate;
        }

        @Override
        public String getId() {
            return delegate.getId();
        }

        @Override
        public String getClientId() {
            return delegate.getClientId();
        }

        @Override
        public String getAccessTokenUri() {
            return delegate.getAccessTokenUri();
        }

        @Override
        public boolean isScoped() {
            return delegate.isScoped();
        }

        @Override
        public List<String> getScope() {
            return delegate.getScope();
        }

        @Override
        public boolean isAuthenticationRequired() {
            return delegate.isAuthenticationRequired();
        }

        @Override
        public String getClientSecret() {
            return delegate.getClientSecret();
        }

        @Override
        public AuthenticationScheme getClientAuthenticationScheme() {
            return delegate.getClientAuthenticationScheme();
        }

        @Override
        public String getGrantType() {
            return delegate.getGrantType();
        }

        @Override
        public AuthenticationScheme getAuthenticationScheme() {
            return delegate.getAuthenticationScheme();
        }

        @Override
        public String getTokenName() {
            return delegate.getTokenName();
        }

        @Override
        public boolean isClientOnly() {
            return true;
        }
    }
}
