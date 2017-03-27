package com.zzj.infomation.controller;

import com.zzj.infomation.service.InfomationService;
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
public class InformationController {

    @Autowired
    InfomationService infomationService;

    /**
     * 查询资讯列表
     * @param type 1文字资讯  2视频资讯
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getInformations")
    public Object getInformations(
            @RequestParam(value = "type", required = true) int type,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        List<Map> informationList = infomationService.getInformations(type);
        result.put("result","success");
        result.put("msg","查询资讯成功");
        result.put("data",informationList);
        return result;
    }


}
