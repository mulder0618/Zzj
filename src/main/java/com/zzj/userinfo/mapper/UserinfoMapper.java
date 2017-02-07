package com.zzj.userinfo.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/14.
 */
@Repository
public interface UserinfoMapper {

    /**
     * 更新用户信息
     * @param params
     * @return
     */
    int updateUserinfo(Map params);

}