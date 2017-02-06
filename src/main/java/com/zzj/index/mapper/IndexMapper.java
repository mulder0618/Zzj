package com.zzj.index.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/14.
 */
@Repository
public interface IndexMapper {

    /**
     * 查询首页技师
     * @param params
     * @return
     */
    List<Map> selectRecommendTechs(Map params);

}