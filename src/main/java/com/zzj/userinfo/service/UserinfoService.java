package com.zzj.userinfo.service;

import com.zzj.mongo.model.Friendship;
import com.zzj.mongo.model.Moments;
import com.zzj.mongo.repository.FriendshipRepository;
import com.zzj.mongo.repository.MomentsRepository;
import com.zzj.userinfo.mapper.UserinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by mulder on 17/2/3.
 */
@Service
public class UserinfoService {

    @Autowired
    private UserinfoMapper userinfoMapper;


    @Autowired
    FriendshipRepository friendshipRepository;


    /**
     * 更新用户信息
     * @param nickName
     * @param status
     * @param userType
     * @param level
     * @param isRecommend
     * @param summary
     * @param headSculpture
     * @param sex
     * @return
     */
    public int updateUserinfo(String uuid,String nickName, String status, String userType, String level, Object isRecommend, String summary, String headSculpture, String sex){
        Map userinfoParam = new HashMap();
        userinfoParam.put("nickName",nickName);
        userinfoParam.put("status",status);
        userinfoParam.put("userType",userType);
        userinfoParam.put("level",level);
        userinfoParam.put("isRecommend",isRecommend);
        userinfoParam.put("summary",summary);
        userinfoParam.put("headSculpture",headSculpture);
        userinfoParam.put("sex",sex);
        userinfoParam.put("uuid",uuid);
        int result = userinfoMapper.updateUserinfo(userinfoParam);
        return result;
    }

    /**
     * 添加好友
     * @param ownerUUID
     * @param friendUUID
     */
    public void setAddFriend(String ownerUUID,String friendUUID){
        Friendship friendship = friendshipRepository.findByOwner(ownerUUID);
        if(friendship == null){
            friendship = new Friendship();
            friendship.setOwner(ownerUUID);
            Set<String> friends = new HashSet();
            friends.add(friendUUID);
            friendship.setFriends(friends);
        }
        else{
            Set<String> friends = friendship.getFriends();
            friends.add(friendUUID);
        }
        friendshipRepository.save(friendship);
    }

    /**
     * 删除好友
     * @param ownerUUID
     * @param friendUUID
     * @return
     */
    public Map setDelFriend(String ownerUUID,String friendUUID){
        Map result = new HashMap();
        Friendship friendship = friendshipRepository.findByOwner(ownerUUID);
        if(friendship == null){
            result.put("result","error");
            result.put("msg","无此用户");
            return result;
        }
        else{
            Set<String> friends = friendship.getFriends();
            friends.remove(friendUUID);
        }
        friendshipRepository.save(friendship);
        result.put("result","success");
        result.put("msg","删除好友成功");
        return result;
    }


}
