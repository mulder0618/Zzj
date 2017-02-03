package com.zzj.login.controller;

import com.zzj.login.service.LoginOrRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mulder on 2016/10/27.
 */
@RestController
public class LoginOrRegisterController {

    @Autowired
    LoginOrRegisterService loginOrRegisterService;

    /**
     * 用户登录
     * @param loginName
     * @param identifyingCode
     * @param sign
     * @param attr
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    public Object login(
            @RequestParam(value = "loginName", required = true) String loginName,
            @RequestParam(value = "identifyingCode", required = true) String identifyingCode,
            @RequestParam(value = "sign", required = true) String sign,
            RedirectAttributes attr,
            HttpServletRequest request
    ) throws Exception {
        //Todo 校验短信验证码
        Map userinfo = loginOrRegisterService.getUserinfo(loginName);
        Map result = new HashMap();
        if(userinfo==null){
            result.put("result","error");
            result.put("msg","无此用户");
        }
        else{
            result.put("result","success");
            result.put("msg","登录成功");
            result.put("userinfo",userinfo);
        }
        return result;
    }


    public Object register(
            @RequestParam(value = "loginName", required = true) String loginName,
            @RequestParam(value = "identifyingCode", required = true) String identifyingCode,
            @RequestParam(value = "sign", required = true) String sign,
            RedirectAttributes attr,
            HttpServletRequest request
    )throws Exception {

        return null;
    }



}
