package com.zzj.index.service;

import com.zzj.index.mapper.IndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mulder on 17/2/3.
 */
@Service
public class IndexService {

    @Autowired
    private IndexMapper indexMapper;


    /**
     * 获取首页推荐技师数据
     * @param size
     * @return
     */
    public List<Map> getRecommendTechs(int size){
        Map techsParam = new HashMap();
        techsParam.put("size",size);
        techsParam.put("status",1);
        List<Map> result = indexMapper.selectRecommendTechs(techsParam);
        return result;
    }

    /**
     * 查询技师
     * @param currentPage
     * @param size
     * @param techName
     * @return
     */
    public List<Map> getTechs(int currentPage,int size,String techName){
        Map techsParam = new HashMap();
        techsParam.put("size",size);
        techsParam.put("currentPage",currentPage*size);
        techsParam.put("status",1);
        techsParam.put("techName",techName);
        List<Map> result = indexMapper.selectTechs(techsParam);
        return result;
    }


}