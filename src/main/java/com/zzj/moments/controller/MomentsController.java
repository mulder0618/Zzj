package com.zzj.moments.controller;

import com.zzj.moments.service.MomentsService;
import com.zzj.mongo.model.Comments;
import com.zzj.mongo.model.Moments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class MomentsController {

    @Autowired
    MomentsService momentsService;


    /**
     * 发表朋友圈
     * @param owner
     * @param message
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/sendMoment")
    public Object sendMoment(
            @RequestParam(value = "owner", required = true) String owner,
            @RequestParam(value = "message", required = true) String message,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        momentsService.setMoment(owner,message);
        result.put("result","success");
        result.put("msg","发表朋友圈成功");
        return result;
    }


    /**
     * 发表评论
     * @param momentsID  朋友圈Id
     * @param ownerUUID  朋友圈发布者Id
     * @param commenterUUID 评论者Id
     * @param friendUUID    @好友Id
     * @param message
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/sendComment")
    public Object sendComment(
            @RequestParam(value = "momentsID", required = true) String momentsID,
            @RequestParam(value = "ownerUUID", required = true) String ownerUUID,
            @RequestParam(value = "commenterUUID", required = true) String commenterUUID,
            @RequestParam(value = "friendUUID", required = false) String friendUUID,
            @RequestParam(value = "message", required = true) String message,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        momentsService.setMomentComment(momentsID,ownerUUID,commenterUUID,friendUUID,message);
        result.put("result","success");
        result.put("msg","发表朋友圈评论成功");
        return result;
    }


    /**
     * 查询朋友圈评论
     * @param userUUID
     * @param momentsID
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
  /*  @RequestMapping("/getMomentComments")
    public Object getMomentComments(
            @RequestParam(value = "userUUID", required = true) String userUUID,
            @RequestParam(value = "momentsID", required = true) String momentsID,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        Comments comments = momentsService.queryMomentsComments(userUUID,momentsID);
        result.put("result","success");
        result.put("msg","查询朋友圈评论成功");
        if(comments != null) {
            result.put("data", comments);
        }
        return result;
    }*/

    /**
     * 获取所有朋友圈内容
     * @param page
     *
     * @param rows
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getAllMoment")
    public Object getAllMoment(
            @RequestParam(value = "userUUID", required = true) String userUUID,
            @RequestParam(value = "page", required = true) int page,
            @RequestParam(value = "rows", required = true) int rows,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        List<Map>  momentses =  momentsService.queryAllMomentsByPage(userUUID,page,rows);
        result.put("result","success");
        result.put("msg","查询朋友圈成功");
        result.put("data",momentses);
        return result;
    }


}
