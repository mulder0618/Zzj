package com.zzj.moments.service;

import com.zzj.moments.mapper.MomentsMapper;
import com.zzj.mongo.model.Moments;
import com.zzj.mongo.repository.MomentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by mulder on 17/2/3.
 */
@Service
public class MomentsService {

    @Autowired
    private MomentsMapper momentsMapper;

    @Autowired
    MomentsRepository momentsRepository;

    /**
     * 发表朋友圈
     * @param owner
     * @param message
     */
    public void setMoment(String owner,String message){
        Moments moments = new Moments();
        moments.setOwner(owner);
        moments.setMessage(message);
        moments.setCreateDate(new Date());
        momentsRepository.insert(moments);
    }

    /**
     * 获取所有朋友圈数据
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    public Page<Moments> queryAllMomentsByPage(int page, int rows) throws Exception {
        PageRequest pageRequest = new PageRequest(page-1,rows,new Sort(Sort.Direction.DESC, "createDate"));
        return momentsRepository.findAll(pageRequest);
    }


}
