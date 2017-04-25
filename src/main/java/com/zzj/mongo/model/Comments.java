package com.zzj.mongo.model;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;

public class Comments {

    @Id
    private String id;

    private String momentsOwner;

    private Set<String> userUUID;

    private String momentsID;

    private String commenterUUID;

    private String commenterNickname;

    private String targetCommentUUID;

    private String targetCommentNickname;

    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(Set<String> userUUID) {
        this.userUUID = userUUID;
    }

    public String getMomentsID() {
        return momentsID;
    }

    public void setMomentsID(String momentsID) {
        this.momentsID = momentsID;
    }

    public String getCommenterUUID() {
        return commenterUUID;
    }

    public void setCommenterUUID(String commenterUUID) {
        this.commenterUUID = commenterUUID;
    }

    public String getTargetCommentUUID() {
        return targetCommentUUID;
    }

    public void setTargetCommentUUID(String targetCommentUUID) {
        this.targetCommentUUID = targetCommentUUID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCommenterNickname() {
        return commenterNickname;
    }

    public void setCommenterNickname(String commenterNickname) {
        this.commenterNickname = commenterNickname;
    }

    public String getTargetCommentNickname() {
        return targetCommentNickname;
    }

    public void setTargetCommentNickname(String targetCommentNickname) {
        this.targetCommentNickname = targetCommentNickname;
    }

    public String getMomentsOwner() {
        return momentsOwner;
    }

    public void setMomentsOwner(String momentsOwner) {
        this.momentsOwner = momentsOwner;
    }

}