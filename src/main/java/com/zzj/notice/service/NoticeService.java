package com.zzj.notice.service;

import com.zzj.loginorregister.mapper.LoginOrRegisterMapper;
import com.zzj.notice.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mulder on 17/2/3.
 */
@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 查询公告信息
     * @param currentDate
     * @return
     */
    public List<Map> getNotice(String currentDate){
        Map noticeParam = new HashMap();
        noticeParam.put("currentDate",currentDate);
        noticeParam.put("status",1);
        List<Map> result = noticeMapper.selectNotice(noticeParam);
        return result;
    }

    /**
     * 插入公告信息
     * @param title
     * @param content
     * @param startDate
     * @param endDate
     */
    public void setNotice(String title,String content,Date startDate,Date endDate){
        Map noticeParam = new HashMap();
        noticeParam.put("title",title);
        noticeParam.put("status",1);
        noticeParam.put("createDate",new Date());
        noticeParam.put("content",content);
        noticeParam.put("startDate",startDate);
        noticeParam.put("endDate",endDate);
        noticeMapper.insertNotice(noticeParam);
    }

}
