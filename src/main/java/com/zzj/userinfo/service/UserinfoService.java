package com.zzj.userinfo.service;

import com.zzj.userinfo.mapper.UserinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mulder on 17/2/3.
 */
@Service
public class UserinfoService {

    @Autowired
    private UserinfoMapper userinfoMapper;

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


}
