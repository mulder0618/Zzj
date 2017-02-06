package com.zzj.index.controller;

import com.zzj.index.service.IndexService;
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
public class IndexController {

    @Autowired
    IndexService indexService;


    @RequestMapping("/getRecommendTechs")
    public Object getRecommendTechs(
            @RequestParam(value = "size", required = true) int size,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        List<Map> recommendTechs = indexService.getRecommendTechs(size);
        result.put("result","success");
        result.put("msg","查询首页推荐技师成功");
        result.put("data",recommendTechs);
        return result;
    }




}
