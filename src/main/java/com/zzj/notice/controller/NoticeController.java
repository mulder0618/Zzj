package com.zzj.notice.controller;

import com.zzj.loginorregister.service.LoginOrRegisterService;
import com.zzj.notice.service.NoticeService;
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
public class NoticeController {

    @Autowired
    NoticeService noticeService;


        @RequestMapping("/getNotice")
    public Object getNotice(
            @RequestParam(value = "currentDate", required = true) String currentDate,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        List<Map> notice = noticeService.getNotice(currentDate);
        Map result = new HashMap();
            if(notice.size()==0){
                result.put("result","error");
                result.put("msg","无公告信息");
            }
            else {
                result.put("result","success");
                result.put("msg","查询公告成功");
                result.put("data",notice);
            }
        return result;
    }



  /*  @RequestMapping("/setNotice")
    public Object setNotice(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = true) String content,
            @RequestParam(value = "startDate", required = true) String startDate,
            @RequestParam(value = "endDate", required = true) String endDate,
            @RequestParam(value = "status", required = true) String status,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        result.put("result","success");
        result.put("msg","登录成功");
        return result;
    }*/


}
