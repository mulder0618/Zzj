package com.zzj.loginorregister.mapper;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
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

    /**
     * 插入用户信息
     * @param params
     */
    void insertUserinfo(Map params);

    /**
     * 查询短信信息
     * @param parMap
     * @return
     */
    String getPhoneCode(Map parMap);

    /**
     * 获取所有工作室
     * @return
     */
    List<Map> selectAllStudio();
}