package com.zzj.advertisement.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/14.
 */
@Repository
public interface AdvertisementMapper {

    /**
     * 查询广告
     * @param params
     * @return
     */
    List<Map> selectAdvert(Map params);

}