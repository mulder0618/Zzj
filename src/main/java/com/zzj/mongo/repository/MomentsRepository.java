package com.zzj.mongo.repository;

import com.zzj.mongo.model.Moments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mulder on 2017/2/14.
 */
public interface MomentsRepository extends MongoRepository<Moments, String> {

    Moments findById(String id);

    Moments findByOwner(String owner);

}
