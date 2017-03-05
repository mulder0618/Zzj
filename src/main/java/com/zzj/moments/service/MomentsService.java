package com.zzj.moments.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zzj.moments.mapper.MomentsMapper;
import com.zzj.mongo.model.Comments;
import com.zzj.mongo.model.Friendship;
import com.zzj.mongo.model.Moments;
import com.zzj.mongo.repository.CommentsRepository;
import com.zzj.mongo.repository.FriendshipRepository;
import com.zzj.mongo.repository.MomentsRepository;
import com.zzj.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mulder on 17/2/3.
 */
@Service
public class MomentsService {

    @Autowired
    private MomentsMapper momentsMapper;

    @Autowired
    MomentsRepository momentsRepository;

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    @Value("${momentsphotosavepath}")
    private String momentsphotosavepath;


    @Value("${momentsphotourl}")
    private String momentsphotourl;

    /**
     * 发表朋友圈
     * @param owner
     * @param message
     */
    public Moments setMoment(String owner,
                          String message,
                          MultipartFile[] photos
    ){
        String headurlPath = null;
        List<String> photoShowList = new ArrayList<>();
        if(photos.length!=0){
            for(MultipartFile photo:photos){
                byte[] bytes = new byte[0];
                try {
                    bytes = photo.getBytes();
                    Date now = new Date();
                    String headName = UUID.randomUUID()+"-"+owner+".jpg";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                    String datepackageName = simpleDateFormat.format(now);
                    headurlPath = momentsphotourl + datepackageName+"/"+headName;
                    photoShowList.add(headurlPath);
                    File dest = new File(momentsphotosavepath+datepackageName+"/"+headName);
                    // 检测是否存在目录
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(dest));
                    stream.write(bytes);
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Moments moments = new Moments();
        moments.setOwner(owner);
        moments.setMessage(message);
        moments.setImages(photoShowList);
        moments.setCreateDate(new Date());
        momentsRepository.insert(moments);
        return moments;
    }


    /**
     * 发表评论
     * @param mementsID
     * @param ownerUUID
     * @param commenterUUID
     * @param tragetCommenterUUID
     * @param message
     */
    public void setMomentComment(String mementsID,String ownerUUID,String commenterUUID,String tragetCommenterUUID,String message){
        //查找好友交集
        Friendship ownerFriends = friendshipRepository.findByOwner(ownerUUID);
        Friendship commenterFriends = friendshipRepository.findByOwner(commenterUUID);
        Set intersectionFriends = null;
        //如果没有评论的评论者 则进行两个交集判断 如果有则在三个人内判断
        if(tragetCommenterUUID==null||"".equals(tragetCommenterUUID)){
            intersectionFriends = MapUtils.intersection2(ownerFriends.getFriends(),commenterFriends.getFriends());
        }
        else{
            Friendship targetFriends = friendshipRepository.findByOwner(tragetCommenterUUID);
            intersectionFriends = MapUtils.intersection3(ownerFriends.getFriends(),commenterFriends.getFriends(),targetFriends.getFriends());
        }
        Comments comments = new Comments();
        comments.setUserUUID(intersectionFriends);
        comments.setMomentsID(mementsID);
        comments.setCommenterUUID(commenterUUID);
        comments.setTargetCommentUUID(tragetCommenterUUID);
        comments.setMessage(message);
        commentsRepository.insert(comments);
        //System.out.println(intersectionFriends);
    }


    /**
     * 通过当前用户ID,朋友圈ID查找朋友圈评论
     * @param userUUID
     * @param momentsID
     * @return
     * @throws Exception
     */
   /* public Comments queryMomentsComments(String userUUID, String momentsID) throws Exception {
        Comments comments = commentsRepository.findByUserUUIDAndMomentsID(userUUID,momentsID);
        return comments;
    }*/

    /**
     * 获取所有朋友圈数据
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    public Map queryAllMomentsByPage(String userUUID,int page, int rows) throws Exception {
        List<Map> momentsList = new ArrayList<>();
        PageRequest pageRequest = new PageRequest(page-1,rows,new Sort(Sort.Direction.DESC, "createDate"));
        Page<Moments>  momentses =  momentsRepository.findAll(pageRequest);
        Map pageInfo = new HashMap();
        pageInfo.put("total",momentses.getTotalPages());
        if(momentses!=null){
            for(Moments moment:momentses ){
                Map momentMap = new HashMap();
                momentMap.put("momentsID",moment.getId());
                momentMap.put("photos",moment.getImages());
                momentMap.put("message",moment.getMessage());
                momentMap.put("momentOwner",moment.getOwner());
                momentMap.put("createDate",moment.getCreateDate());
                Map commentsMap = new HashMap();
                String momentID = moment.getId();
                List<Comments> commentes = commentsRepository.findByUserUUIDAndMomentsID(userUUID,momentID);
                if(commentes.size()!=0){
                    List<Map> commentsList = new ArrayList<>();
                    for(Comments comments:commentes){
                        commentsMap.put("commenterUUID",comments.getCommenterUUID());
                        commentsMap.put("targetCommenterUUID",comments.getTargetCommentUUID());
                        commentsMap.put("message",comments.getMessage());
                        commentsList.add(commentsMap);
                    }
                    momentMap.put("comments",commentsList);
                }
                momentsList.add(momentMap);
            }
        }
        pageInfo.put("list",momentsList);
        return pageInfo;
    }


}
