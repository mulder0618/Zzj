package com.zzj.loginorregister.service;

import com.zzj.loginorregister.mapper.LoginOrRegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public void setUserinfo(String loginName,String userType){
        Map loginParam = new HashMap();
        loginParam.put("loginName",loginName);
        loginParam.put("status",1);
        loginParam.put("createDate",new Date());
        loginParam.put("userType",userType);
        loginOrRegisterMapper.insertUserinfo(loginParam);
    }

}
