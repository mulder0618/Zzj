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


    /**
     * 添加好友关系
     * @param params
     */
    void insertFriendship(Map params);


    /**
     * 获取好友列表
     * @param params
     * @return
     */
    List<Map> getMyFriendship(Map params);

    /**
     * 查找好友
     * @param params
     * @return
     */
    List<Map> searchFriend(Map params);


    /**
     * 获取当前用户信息
     * @param params
     * @return
     */
    Map getUserInfo(Map params);

    /**
     * 获取服务列表
     * @param params
     * @return
     */
    List<Map> getService(Map params);


    /**
     * 预约
     * @param params
     */
    void insertSubscribe(Map params);


}