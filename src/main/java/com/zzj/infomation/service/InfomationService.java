package com.zzj.infomation.service;

import com.zzj.infomation.mapper.InfomationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mulder on 17/2/3.
 */
@Service
public class InfomationService {

    @Autowired
    private InfomationMapper infomationMapper;

    /**
     * 查询资讯
     * @param type
     * @return
     */
    public List<Map> getInformations(int type){
        Map param = new HashMap();
        param.put("type",type);
        List<Map> result = infomationMapper.selectInformations(param);
        return result;
    }

}
