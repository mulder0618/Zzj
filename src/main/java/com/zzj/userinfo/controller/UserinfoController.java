package com.zzj.userinfo.controller;

import com.zzj.userinfo.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mulder on 2016/10/27.
 */
@RestController
public class UserinfoController {

    @Autowired
    UserinfoService userinfoService;


    @RequestMapping("/setUserinfo")
    public Object setUserinfo(
            @RequestParam(value = "uuid", required = true) String uuid,
            @RequestParam(value = "nickName", required = false) String nickName,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "userType", required = false) String userType,
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "isRecommend", required = false) String isRecommend,
            @RequestParam(value = "summary", required = false) String summary,
            @RequestParam(value = "headSculpture", required = false) String headSculpture,
            @RequestParam(value = "sex", required = false) String sex,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        int userUpdateresult = userinfoService.updateUserinfo(uuid,nickName,status,userType,level,isRecommend,summary,headSculpture,sex);
        result.put("result","success");
        result.put("msg","更新用户信息成功");
        return result;
    }




}
