package com.zzj.demo.model;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mulder on 2017/2/14.
 */
public interface  DemoInfoRepository  extends MongoRepository<Demo, String> {
    Demo findByName(String name);
}
