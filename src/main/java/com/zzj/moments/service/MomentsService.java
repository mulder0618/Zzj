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
        Set intersectionFriends = MapUtils.intersection(ownerFriends.getFriends(),commenterFriends.getFriends());
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
    public Comments queryMomentsComments(String userUUID, String momentsID) throws Exception {
        Comments comments = commentsRepository.findByUserUUIDAndMomentsID(userUUID,momentsID);
        return comments;
    }

    /**
     * 获取所有朋友圈数据
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    public Page<Moments> queryAllMomentsByPage(int page, int rows) throws Exception {
        PageRequest pageRequest = new PageRequest(page-1,rows,new Sort(Sort.Direction.DESC, "createDate"));
        return momentsRepository.findAll(pageRequest);
    }


}
