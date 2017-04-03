package com.zzj.loginorregister.service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.zzj.loginorregister.mapper.LoginOrRegisterMapper;
import com.zzj.mongo.model.Friendship;
import com.zzj.mongo.repository.FriendshipRepository;
import com.zzj.utils.UUIDUtils;
import com.zzj.utils.chat.ClientContext;
import com.zzj.utils.chat.EasemobRestAPIFactory;
import com.zzj.utils.chat.api.IMUserAPI;
import com.zzj.utils.chat.comm.body.IMUserBody;
import com.zzj.utils.chat.comm.wrapper.BodyWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mulder on 17/2/3.
 */
@Service
public class LoginOrRegisterService {

    @Autowired
    private LoginOrRegisterMapper loginOrRegisterMapper;

    /**
     * 获取用户信息
     * @param loginName
     * @return
     */
    public Map getUserinfo(String loginName){
        Map loginParam = new HashMap();
        loginParam.put("loginName",loginName);
        loginParam.put("status",1);
        Map result = loginOrRegisterMapper.selectUserinfo(loginParam);
        return result;
    }


    @Autowired
    FriendshipRepository friendshipRepository;

    /**
     * 插入用户信息
     * @param loginName
     * @param userType
     */
    public Map setUserinfo(String loginName,String userType,String nickname,String studio){
        Map loginParam = new HashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
        String second = simpleDateFormat.format(new Date());
        String userUUID = UUIDUtils.generateShortUuid()+second;
        loginParam.put("loginName",loginName);
        loginParam.put("status",1);
        loginParam.put("createDate",new Date());
        loginParam.put("userType",userType);
        loginParam.put("nickName",nickname);
        loginParam.put("studio",studio);
        loginParam.put("uuid",userUUID);
        try {
            loginOrRegisterMapper.insertUserinfo(loginParam);
        }
        catch(Exception e){
            //用户名重复错误
            if(e.getMessage().contains("loginName_unique")){
                loginParam.put("operateStatus","error");
                loginParam.put("msg","用户名已经注册");
            }
            return loginParam;
        }
        //mongo设置默认好友 防止当前用户无好友时空指针
        Friendship friendship = new Friendship();
        friendship.setOwner(userUUID);
        Set<String> friends = new HashSet();
        friends.add("init");
        friendship.setFriends(friends);
        friendshipRepository.save(friendship);
        //注册环信
        EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
        IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
        BodyWrapper userBody = new IMUserBody(userUUID, "123456", "");
        user.createNewIMUserSingle(userBody);
        loginParam.put("operateStatus","success");
        return  loginParam;
    }

   /* public static void  main(String[] args){
        EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
        IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
        user.getIMUsersBatch(1L,"");
    }*/
    /**
     * 获取短信验证码
     * @param mobile
     * @return
     */
    public String getSmsContent(String mobile){
        Map param = new HashMap();
        param.put("mobile",mobile);
        String smsContent = loginOrRegisterMapper.getPhoneCode(param);
        return smsContent;
    }


    /**
     * 判断短信内容是否正确 成功返回true 失败返回false
     * @param userSmsContent
     * @param mobile
     * @return
     */
    public boolean isSmsVerify(String userSmsContent,String mobile){
        String content = getSmsContent(mobile);
        if(content.equals(userSmsContent)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 获取所有工作室列表
     * @return
     */
    public List<Map> getAllStudio(){
        return loginOrRegisterMapper.selectAllStudio();
    }

}
