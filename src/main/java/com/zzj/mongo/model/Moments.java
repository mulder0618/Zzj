package com.zzj.mongo.model;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Moments {

    @Id
    private String id;

    private String owner;

    private List<String> images;

    private String message;

    private List<Map> comment;

    private Date createDate;

    public List<Map> getComment() {
        return comment;
    }

    public void setComment(List<Map> comment) {
        this.comment = comment;
    }

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

    @Override
    public String toString() {
        return "Moments{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", images=" + images +
                ", message='" + message + '\'' +
                ", comment=" + comment +
                '}';
    }
}