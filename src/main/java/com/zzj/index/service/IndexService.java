package com.zzj.index.service;

import com.zzj.index.mapper.IndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public List<Map> getTechs(int currentPage,int size,String techName,String owner){
        Map techsParam = new HashMap();
        techsParam.put("size",size);
        techsParam.put("currentPage",currentPage*size);
        techsParam.put("status",1);
        techsParam.put("techName",techName);
        techsParam.put("owner",owner);
        List<Map> result = indexMapper.selectTechs(techsParam);
        return result;
    }


    /**
     * 获取安卓更新信息
     * @return
     */
    public Map getAndroidInfo(){
        return indexMapper.selectAndroidInfo();
    }

    /**
     * 更新安卓信息
     * @param versionCode
     * @param versionName
     * @param isForce
     * @param apkUrl
     * @return
     */
    public Map setAndroidInfo(String versionCode,String versionName,String isForce,String apkUrl){
        Map param = new HashMap();
        param.put("versionCode",versionCode);
        param.put("versionName",versionName);
        param.put("isForce",isForce);
        param.put("apkUrl",apkUrl);
        param.put("createTime",new Date());
        indexMapper.setAndroidInfo(param);
        return param;
    }
}
