package com.zzj.advertisement.service;

import com.zzj.advertisement.mapper.AdvertisementMapper;
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
public class AdvertisementService {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    /**
     * 获取广告信息
     * @param postition
     * @return
     */
    public List<Map> getAdvert(String postition){
        Map advertParam = new HashMap();
        advertParam.put("position",postition);
        advertParam.put("status",1);
        List<Map> result = advertisementMapper.selectAdvert(advertParam);
        return result;
    }


}
