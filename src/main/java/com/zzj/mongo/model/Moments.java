package com.zzj.mongo.model;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Moments {

    @Id
    private String id;

    private String owner;

    private String ownerNickname;

    private String ownerHead;

    private List<String> images;

    private String message;

    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOwnerNickname() {
        return ownerNickname;
    }

    public void setOwnerNickname(String ownerNickname) {
        this.ownerNickname = ownerNickname;
    }

    public String getOwnerHead() {
        return ownerHead;
    }

    public void setOwnerHead(String ownerHead) {
        this.ownerHead = ownerHead;
    }

    @Override
    public String toString() {
        return "Moments{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", images=" + images +
                ", message='" + message + '\'' +
                '}';
    }
}