package com.foomoo.awf.onedrive;

import com.foomoo.awf.oauth2.AccessTokenRepository;
import com.foomoo.awf.oauth2.PersistableDefaultOAuth2AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * ClientTokenServices implementation for managing (persisting) a single token.
 * Useful for cases where an application needs to maintain access to a resource regardless of the
 * current users.
 */
public class SingleTokenClientTokenServices implements ClientTokenServices {

    private final Integer tokenId;
    private final AccessTokenRepository accessTokenRepository;

    /**
     * Construct the this token servics to manage a single token with the given token id.
     *
     * @param tokenId The id of the token to manage.
     */
    public SingleTokenClientTokenServices(final Integer tokenId, final AccessTokenRepository accessTokenRepository) {
        this.tokenId = tokenId;
        this.accessTokenRepository = accessTokenRepository;
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        return accessTokenRepository.findOne(tokenId);
    }

    @Override
    public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication, OAuth2AccessToken accessToken) {
        final PersistableDefaultOAuth2AccessToken persistableAccessToken = new PersistableDefaultOAuth2AccessToken(accessToken);
        persistableAccessToken.id = tokenId;
        accessTokenRepository.save(persistableAccessToken);
    }

    @Override
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        accessTokenRepository.delete(tokenId);
    }
}
