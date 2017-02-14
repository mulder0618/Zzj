package com.zzj.demo.controller;

import com.zzj.mongo.model.Moments;
import com.zzj.mongo.repository.MomentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mulder on 2017/2/14.
 */
@Controller
public class DemoInfo {

    @Autowired
    MomentsRepository momentsRepository;


    public void testInsert(){
        Moments moments = new Moments();
        moments.setOwner("FFDS-11-HHH");
        List<String> images = new ArrayList();
        images.add("http://www.aa.com/a.jpg");
        images.add("http://www.aa.com/b.jpg");
        moments.setImages(images);
        moments.setMessage("这是第一条测试朋友圈");
        List<Map> commentList= new ArrayList();
        Map commentKey = new HashMap();
        Map comment = new HashMap();
        comment.put("评论了张三","这是测试评论");
        commentKey.put("1",comment);
        commentList.add(commentKey);
        moments.setComment(commentList);
        System.out.println(momentsRepository.insert(moments));
    }

    public Moments testSelect(){
        Moments moments = new Moments();
        moments.setOwner("FFDS-11-HHH");
        return momentsRepository.findByOwner(moments.getOwner());
    }

        @RequestMapping("/testmongoinsert")
    public String testmongo(){
        this.testInsert();
        return null;
    }


    @RequestMapping("/testmongoselect")
    public String testmongoselect(){
        Moments moments = this.testSelect();
        System.out.println(moments.toString());
        return null;
    }

}
