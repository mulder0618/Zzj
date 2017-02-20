package com.zzj.mongo.repository;

import com.zzj.mongo.model.Comments;
import com.zzj.mongo.model.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by mulder on 2017/2/14.
 */
public interface CommentsRepository extends MongoRepository<Comments, String> {

    Comments findByUserUUID(String userUUID);

    List<Comments> findByUserUUIDAndMomentsID(String userUUID, String momentsID);

}
