package com.zzj.userinfo.controller;

import com.zzj.userinfo.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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


    /**
     * 新增用户
     * @param uuid
     * @param nickName
     * @param status
     * @param userType
     * @param level
     * @param isRecommend
     * @param summary
     * @param headSculpture
     * @param sex
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setUserinfo", method= RequestMethod.POST)
    public Object setUserinfo(
            @RequestParam(value = "uuid", required = true) String uuid,
            @RequestParam(value = "nickName", required = false) String nickName,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "userType", required = false) String userType,
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "isRecommend", required = false) String isRecommend,
            @RequestParam(value = "summary", required = false) String summary,
            @RequestParam(value = "headSculpture",required = false) MultipartFile headSculpture,
            @RequestParam(value = "sex", required = false) String sex,
            @RequestParam(value = "studio", required = false) String studio,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        Map userUpdateresult = userinfoService.updateUserinfo(uuid,nickName,status,userType,level,isRecommend,summary,headSculpture,sex,studio);
        result.put("result","success");
        result.put("msg","更新用户信息成功");
        result.put("data",userUpdateresult);
        return result;
    }


    /**
     * 添加好友
     * @param ownerUUID
     * @param friendUUID
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/addFriend")
    public Object addFriend(
            @RequestParam(value = "ownerUUID", required = true) String ownerUUID,
            @RequestParam(value = "friendUUID", required = true) String friendUUID,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        userinfoService.setAddFriend(ownerUUID,friendUUID);
        result.put("result","success");
        result.put("msg","添加好友成功");
        return result;
    }

    /**
     * 删除好友
     * @param ownerUUID
     * @param friendUUID
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/delFriend")
    public Object delFriend(
            @RequestParam(value = "ownerUUID", required = true) String ownerUUID,
            @RequestParam(value = "friendUUID", required = true) String friendUUID,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        Map operResult = userinfoService.setDelFriend(ownerUUID,friendUUID);
        result.put("result",operResult.get("result"));
        result.put("msg",operResult.get("msg"));
        return result;
    }


    /**
     * 查找朋友
     * @param searchTitle
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchFriend")
    public Object searchFriend(
            @RequestParam(value = "searchTitle", required = true) String searchTitle,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        List<Map> searchResult = userinfoService.searchFriend(searchTitle);
        result.put("result","success");
        result.put("data",searchResult);
        return result;
    }


    /**
     * 获取我的好友列表
     * @param ownerUUID
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getMyFriendship")
    public Object getMyFriendship(
            @RequestParam(value = "ownerUUID", required = true) String ownerUUID,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        List<Map> myFriendship = userinfoService.getMyFriendship(ownerUUID);
        result.put("result","success");
        result.put("data",myFriendship);
        return result;
    }


    /**
     * 获取用户信息
     * @param owner
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getUserinfo")
    public Object getUserinfo(
            @RequestParam(value = "owner", required = true) String owner,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        Map userinfo = userinfoService.getUserinfo(owner);
        result.put("result","success");
        result.put("data",userinfo);
        return result;
    }


}
