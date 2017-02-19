package com.zzj.mongo.repository;

import com.zzj.mongo.model.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mulder on 2017/2/14.
 */
public interface FriendshipRepository extends MongoRepository<Friendship, String> {

    Friendship findByOwner(String owner);

}
