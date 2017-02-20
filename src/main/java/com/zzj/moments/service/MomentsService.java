package com.zzj.moments.service;

import com.zzj.moments.mapper.MomentsMapper;
import com.zzj.mongo.model.Comments;
import com.zzj.mongo.model.Friendship;
import com.zzj.mongo.model.Moments;
import com.zzj.mongo.repository.CommentsRepository;
import com.zzj.mongo.repository.FriendshipRepository;
import com.zzj.mongo.repository.MomentsRepository;
import com.zzj.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    /**
     * 发表朋友圈
     * @param owner
     * @param message
     */
    public void setMoment(String owner,String message){
        Moments moments = new Moments();
        moments.setOwner(owner);
        moments.setMessage(message);
        moments.setCreateDate(new Date());
        momentsRepository.insert(moments);
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
    public List<Map> queryAllMomentsByPage(String userUUID,int page, int rows) throws Exception {
        List<Map> momentsList = new ArrayList<>();
        PageRequest pageRequest = new PageRequest(page-1,rows,new Sort(Sort.Direction.DESC, "createDate"));
        Page<Moments>  momentses =  momentsRepository.findAll(pageRequest);
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
                Comments comments = commentsRepository.findByUserUUIDAndMomentsID(userUUID,momentID);
                if(comments!=null){
                    commentsMap.put("commenterUUID",comments.getCommenterUUID());
                    commentsMap.put("targetCommenterUUID",comments.getTargetCommentUUID());
                    commentsMap.put("message",comments.getMessage());
                    momentMap.put("comments",commentsMap);
                }
                momentsList.add(momentMap);
            }
        }
        return momentsList;
    }


}
