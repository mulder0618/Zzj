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

    /**
     * 首页推荐技师
     * @param size
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
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


    /**
     * 搜索技师
     * @param currentPage
     * @param size
     * @param techName
     * @param sign
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchTechs")
    public Object searchTechs(
            @RequestParam(value = "currentPage", required = true) int currentPage,
            @RequestParam(value = "size", required = true) int size,
            @RequestParam(value = "techName", required = true) String techName,
            @RequestParam(value = "owner", required = true) String owner,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        List<Map> techs = indexService.getTechs(currentPage-1,size,techName,owner);
        result.put("result","success");
        result.put("msg","搜索技师技师成功");
        result.put("data",techs);
        return result;
    }



    @RequestMapping("/getAndroidInfo")
    public Object getAndroidInfo(
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        Map info = indexService.getAndroidInfo();
        result.put("result","success");
        result.put("msg","获取安卓更新信息成功");
        result.put("data",info);
        return result;
    }

    @RequestMapping("/setAndroidInfo")
    public Object getAndroidInfo(
            @RequestParam(value = "versionCode", required = true) String versionCode,
            @RequestParam(value = "versionName", required = true) String versionName,
            @RequestParam(value = "isForce", required = true) String isForce,
            @RequestParam(value = "apkUrl", required = false) String apkUrl,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        Map info = indexService.setAndroidInfo(versionCode,versionName,isForce,apkUrl);
        result.put("result","success");
        result.put("msg","安卓更新信息成功");
        result.put("data",info);
        return result;
    }



}
