package com.zzj.userinfo.service;

import com.zzj.mongo.model.Friendship;
import com.zzj.mongo.model.Moments;
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
                              String sex){
        //上传头像图片
        String headurlPath = null;
        if(headSculpture!=null){
            byte[] bytes = new byte[0];
            try {
                bytes = headSculpture.getBytes();
                String headName = uuid+"-head.jpg";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                String datepackageName = simpleDateFormat.format(new Date());
                headurlPath = headurl + datepackageName+"/"+headName;
                File dest = new File(imgsavepath+datepackageName+"/"+headName);
                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(dest));
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
        int result = userinfoMapper.updateUserinfo(userinfoParam);
        return userinfoParam;
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



}
