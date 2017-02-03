package com.zzj.login.mapper;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by admin on 2016/12/14.
 */
@Repository
public interface LoginOrRegisterMapper {

    /**
     * 查找用户信息
     * @param params
     * @return
     */
    Map selectUserinfo(Map params);
}