package com.zzj.userinfo.service;

import com.zzj.mongo.model.Comments;
import com.zzj.mongo.model.Friendship;
import com.zzj.mongo.model.Moments;
import com.zzj.mongo.repository.CommentsRepository;
import com.zzj.mongo.repository.FriendshipRepository;
import com.zzj.mongo.repository.MomentsRepository;
import com.zzj.userinfo.mapper.UserinfoMapper;
import com.zzj.utils.chat.ClientContext;
import com.zzj.utils.chat.EasemobRestAPIFactory;
import com.zzj.utils.chat.api.IMUserAPI;
import com.zzj.utils.chat.comm.body.IMUserBody;
import com.zzj.utils.chat.comm.wrapper.BodyWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

    @Autowired
    CommentsRepository commentsRepository;

    @Value("${imgsavepath}")
    private String imgsavepath;

    @Value("${headurl}")
    private String headurl;

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
    public Map updateUserinfo(String uuid,
                              String nickName,
                              String status,
                              String userType,
                              String level,
                              Object isRecommend,
                              String summary,
                              MultipartFile headSculpture,
                              String sex,
                              String studio
    ){
        //上传头像图片
        String headurlPath = null;
        if(headSculpture!=null){
            byte[] bytes = new byte[0];
            try {
                bytes = headSculpture.getBytes();
                String headName = uuid+"-head.jpg";
                //headurlPath = headurl +headName;
                File dest = new File(imgsavepath+headName);
                //删除已有头像
                dest.delete();
                //生成新头像
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                String datepackageName = simpleDateFormat.format(new Date());
                String newheadName = uuid+datepackageName+"-head.jpg";
                headurlPath = headurl +newheadName;
                File destNew = new File(imgsavepath+newheadName);
                // 检测是否存在目录
                if (!destNew.getParentFile().exists()) {
                    destNew.getParentFile().mkdirs();
                }
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(destNew));
                stream.write(bytes);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map userinfoParam = new HashMap();
        userinfoParam.put("nickName",nickName);
        userinfoParam.put("status",status);
        userinfoParam.put("userType",userType);
        userinfoParam.put("level",level);
        userinfoParam.put("isRecommend",isRecommend);
        userinfoParam.put("summary",summary);
        userinfoParam.put("headSculpture",headurlPath);
        userinfoParam.put("sex",sex);
        userinfoParam.put("uuid",uuid);
        userinfoParam.put("studio",studio);
        int result = userinfoMapper.updateUserinfo(userinfoParam);

        Map userinfoQuery =  new HashMap();
        userinfoQuery.put("owner",uuid);
        Map reultMap = userinfoMapper.getUserInfo(userinfoQuery);
        return reultMap;
    }

    /**
     * 添加好友模块
     * @param uuid1         添加人
     * @param uuid2         被添加人
     * @param friendship    添加人好友关系
     * @return
     */
    private Friendship setFriendShip(String uuid1,String uuid2,Friendship friendship){
        //第一次加好友
        if(friendship == null){
            friendship = new Friendship();
            friendship.setOwner(uuid1);
            Set<String> friends = new HashSet();
            friends.add(uuid2);
            friendship.setFriends(friends);
        }
        else{   //非第一次添加 增量更新
            Set<String> friends = friendship.getFriends();
            friends.add(uuid2);
        }
        return friendship;
    }

    /**
     * 添加好友
     * @param ownerUUID
     * @param friendUUID
     */
    public void setAddFriend(String ownerUUID,String friendUUID){
        //------mongodb   部分
        //当前用户添加
        Friendship friendship1 = friendshipRepository.findByOwner(ownerUUID);
        friendship1 = setFriendShip(ownerUUID,friendUUID,friendship1);
        friendshipRepository.save(friendship1);

        //被添加用户也添加关系
        Friendship friendship2 = friendshipRepository.findByOwner(friendUUID);
        friendship2 = setFriendShip(friendUUID,ownerUUID,friendship2);
        friendshipRepository.save(friendship2);

        //----------评论部分可见
        List<Comments> commentses = commentsRepository.findByMomentsOwner(ownerUUID);
        for(Comments comment : commentses){
            Set<String> userUUIDs = comment.getUserUUID();
            userUUIDs.add(friendUUID);  //添加好友可见
            comment.setUserUUID(userUUIDs);
            commentsRepository.save(comment);
        }

        List<Comments> commentsesFriend = commentsRepository.findByMomentsOwner(friendUUID);
        for(Comments comment : commentsesFriend){
            Set<String> userUUIDs = comment.getUserUUID();
            userUUIDs.add(ownerUUID);  //添加好友可见
            comment.setUserUUID(userUUIDs);
            commentsRepository.save(comment);
        }


        //-------mysql 部分
        //当前用户添加
        Map mysqlFriendship = new HashMap();
        mysqlFriendship.put("owner",ownerUUID);
        mysqlFriendship.put("friend",friendUUID);
        userinfoMapper.insertFriendship(mysqlFriendship);
        //被添加用户关系
        mysqlFriendship.put("owner",friendUUID);
        mysqlFriendship.put("friend",ownerUUID);
        userinfoMapper.insertFriendship(mysqlFriendship);

        //添加环信好友
        EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
        IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
        user.addFriendSingle(ownerUUID,friendUUID);
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

    /**
     * 获取好友列表
     * @param ownerUUID
     * @return
     */
    public List<Map> getMyFriendship(String ownerUUID){
        Map myFriendship = new HashMap();
        myFriendship.put("owner",ownerUUID);
        return userinfoMapper.getMyFriendship(myFriendship);
    }


    /**
     * 查找好友
     * @param searchTitle
     * @return
     */
    public List<Map> searchFriend(String searchTitle){
        Map search = new HashMap();
        search.put("nikcName",searchTitle);
        search.put("loginName",searchTitle);
        return userinfoMapper.searchFriend(search);
    }


    /**
     * 获取用户信息
     * @param owner
     * @return
     */
    public Map getUserinfo(String owner){
        Map search = new HashMap();
        search.put("owner",owner);
        return userinfoMapper.getUserInfo(search);
    }

    /**
     * 获取服务列表
     * @param techUuid
     * @return
     */
    public List<Map> getService(String techUuid){
        Map search = new HashMap();
        search.put("techUuid",techUuid);
        return userinfoMapper.getService(search);
    }

    /**
     * 预约
     * @param userUuid
     * @param techUuid
     * @param startDate
     * @param endDate
     * @param service
     */
    public void subScribe(String userUuid,String techUuid,String startDate,String endDate,String service){
        Map subscribe = new HashMap();
        subscribe.put("userUuid",userUuid);
        subscribe.put("techUuid",techUuid);
        subscribe.put("startDate",startDate);
        subscribe.put("endDate",endDate);
        subscribe.put("service",service);
        subscribe.put("status",1);
        userinfoMapper.insertSubscribe(subscribe);
    }

}
