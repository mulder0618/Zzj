package com.zzj.mongo.repository;

import com.zzj.mongo.model.Moments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by mulder on 2017/2/14.
 */
public interface MomentsRepository extends MongoRepository<Moments, String> {

    Moments findById(String id);

    List<Moments> findByOwner(String owner);

    Page<Moments> findByOwner(String owner,Pageable page) ;

}
