package com.zzj.loginorregister.controller;

import com.zzj.loginorregister.service.LoginOrRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
        Map result = new HashMap();
        //todo  上线前务必开启
     /*   if(!loginOrRegisterService.isSmsVerify(identifyingCode,loginName)){
            result.put("result","error");
            result.put("msg","短信验证码错误");
            return result;
        }*/
        Map userinfo = loginOrRegisterService.getUserinfo(loginName);

        if(userinfo==null){
            result.put("result","error");
            result.put("msg","无此用户");
        }
        else{
            result.put("result","success");
            result.put("msg","登录成功");
            result.put("data",userinfo);
        }
        return result;
    }


    /**
     * 注册接口
     * @param loginName
     * @param identifyingCode
     * @param regType   1 技师 2 用户
     * @param sign
     * @param attr
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/register")
    public Object register(
            @RequestParam(value = "loginName", required = true) String loginName,
            @RequestParam(value = "identifyingCode", required = true) String identifyingCode,
            @RequestParam(value = "regType", required = true) String regType,
            @RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "studio", required = false) String studio,
            @RequestParam(value = "sign", required = true) String sign,
            RedirectAttributes attr,
            HttpServletRequest request
    )throws Exception {
        Map result = new HashMap();
        //todo  上线前务必开启
      /*  if(!loginOrRegisterService.isSmsVerify(identifyingCode,loginName)){
            result.put("result","error");
            result.put("msg","短信验证码错误");
            return result;
        }*/
        //保存数据
        Map loginResult = loginOrRegisterService.setUserinfo(loginName,regType,nickname,studio);
        if(loginResult.get("operateStatus").equals("success")){
            result.put("result","success");
            result.put("msg","注册成功");
            result.put("data",loginResult);
        }
        else{
            result.put("result","error");
            result.put("msg",loginResult.get("msg"));
        }
        return result;
    }

    /**
     * 获取工作室列表
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getAllStudio")
    public Object getAllStudio(
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    )throws Exception {
        Map result = new HashMap();
        List<Map> studioList = loginOrRegisterService.getAllStudio();
        result.put("result","success");
        result.put("msg","获取工作室成功");
        result.put("data",studioList);
        return result;
    }
}
