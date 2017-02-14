package com.zzj.demo.controller;

import com.zzj.demo.model.Demo;
import com.zzj.demo.model.DemoInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mulder on 2017/2/14.
 */
@Controller
public class DemoInfo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    DemoInfoRepository demoInfoRepository;


    public void test(){
        Demo demo = new Demo();
        demo.setName("mulser");
        System.out.println(demoInfoRepository.insert(demo));
    }

    @RequestMapping("/testmongo")
    public String testmongo(){
        this.test();
        return null;
    }

}
