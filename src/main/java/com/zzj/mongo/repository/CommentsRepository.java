package com.zzj.mongo.repository;

import com.zzj.mongo.model.Comments;
import com.zzj.mongo.model.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mulder on 2017/2/14.
 */
public interface CommentsRepository extends MongoRepository<Comments, String> {

    Comments findByUserUUID(String userUUID);

    Comments findByUserUUIDAndMomentsID(String userUUID,String momentsID);

}
