package com.rovaindu.homeservice.model;

import java.io.Serializable;

public class ComplainComments implements Serializable {


    private int id;
    private int userID;
    private String name;
    private String thumb_image;
    private String content;
    private int likes;
    private int comments;
    private long created_at;


    public ComplainComments()
    {

    }

    public ComplainComments(int id, int userID, String name, String thumb_image, String content, int likes, int comments, long created_at) {
        this.id = id;
        this.userID = userID;
        this.name = name;
        this.thumb_image = thumb_image;
        this.content = content;
        this.likes = likes;
        this.comments = comments;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
}