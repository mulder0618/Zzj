package com.zzj.moments.controller;

import com.zzj.moments.service.MomentsService;
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


    @RequestMapping("/sendComment")
    public Object sendComment(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "ownerUUID", required = true) String ownerUUID,
            @RequestParam(value = "friendUUID", required = false) String friendUUID,
            @RequestParam(value = "message", required = true) String message,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        momentsService.setMomentComment(id,ownerUUID,friendUUID,message);
        result.put("result","success");
        result.put("msg","发表朋友圈评论成功");
        return result;
    }


    /**
     * 获取所有朋友圈内容
     * @param page
     * @param rows
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getAllMoment")
    public Object getAllMoment(
            @RequestParam(value = "page", required = true) int page,
            @RequestParam(value = "rows", required = true) int rows,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        Page<Moments>  momentses =  momentsService.queryAllMomentsByPage(page,rows);
        result.put("result","success");
        result.put("msg","查询朋友圈成功");
        result.put("data",momentses.getContent());
        return result;
    }


}
