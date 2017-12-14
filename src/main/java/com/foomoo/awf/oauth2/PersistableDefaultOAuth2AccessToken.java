package com.foomoo.awf.oauth2;

import org.springframework.data.annotation.Id;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class PersistableDefaultOAuth2AccessToken extends DefaultOAuth2AccessToken {

    @Id
    public int id;

    public PersistableDefaultOAuth2AccessToken(String value) {
        super(value);
    }

    public PersistableDefaultOAuth2AccessToken(OAuth2AccessToken accessToken) {
        super(accessToken);
    }

    /**
     * Private constructor for JPA and other serialization tools.
     */
    @SuppressWarnings("unused")
    private PersistableDefaultOAuth2AccessToken() {
        super((String) null);
    }
}
