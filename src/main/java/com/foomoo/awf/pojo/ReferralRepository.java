package com.foomoo.awf.pojo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ReferralRepository extends MongoRepository<Referral, UUID> {

}