package com.foomoo.awf.oauth2;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccessTokenRepository extends MongoRepository<PersistableDefaultOAuth2AccessToken, Integer> {
}
