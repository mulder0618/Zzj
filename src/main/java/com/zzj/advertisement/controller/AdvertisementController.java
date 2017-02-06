package com.zzj.advertisement.controller;

import com.zzj.advertisement.service.AdvertisementService;
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
public class AdvertisementController {

    @Autowired
    AdvertisementService advertisementService;


    @RequestMapping("/getIndexAdvert")
    public Object getIndexAdvert(
            @RequestParam(value = "position", required = true) String position,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        List<Map> adverts = advertisementService.getAdvert(position);
        result.put("result","success");
        result.put("msg","查询首页广告成功");
        result.put("data",adverts);
        return result;
    }




}
