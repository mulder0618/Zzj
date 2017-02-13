package com.zzj.infomation.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/14.
 */
@Repository
public interface InfomationMapper {


    List<Map> selectInformation(Map params);

}