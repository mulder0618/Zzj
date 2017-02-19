package com.zzj.mongo.model;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Friendship {

    @Id
    private String id;

    private String owner;

    private Set friends;

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

    public Set getFriends() {
        return friends;
    }

    public void setFriends(Set friends) {
        this.friends = friends;
    }
}