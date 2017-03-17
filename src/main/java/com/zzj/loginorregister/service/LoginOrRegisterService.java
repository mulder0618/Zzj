package com.zzj.loginorregister.service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.zzj.loginorregister.mapper.LoginOrRegisterMapper;
import com.zzj.utils.UUIDUtils;
import com.zzj.utils.chat.ClientContext;
import com.zzj.utils.chat.EasemobRestAPIFactory;
import com.zzj.utils.chat.api.IMUserAPI;
import com.zzj.utils.chat.comm.body.IMUserBody;
import com.zzj.utils.chat.comm.wrapper.BodyWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    /**
     * 插入用户信息
     * @param loginName
     * @param userType
     */
    public Map setUserinfo(String loginName,String userType){
        Map loginParam = new HashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
        String second = simpleDateFormat.format(new Date());
        String userUUID = UUIDUtils.generateShortUuid()+second;
        loginParam.put("loginName",loginName);
        loginParam.put("status",1);
        loginParam.put("createDate",new Date());
        loginParam.put("userType",userType);
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
}
