package com.zzj.notice.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/14.
 */
@Repository
public interface NoticeMapper {

    /**
     * 查询公告
     * @param params
     * @return
     */
    List<Map> selectNotice(Map params);

    /**
     * 插入公告
     * @param params
     */
    void insertNotice(Map params);
}