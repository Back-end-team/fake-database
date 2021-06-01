package com.backend.fakedb.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table
public class PostEntity {

    // The associated ID
    @Id
    @GeneratedValue
    Integer id;

    // A URL to a thumbnail picture
    String thumbnail;

    // A title for the post
    String title;

    // A short description associated with the current post
    String description;

    // The date when the post was published
    String postDate;

    // The associated score, which can be either TRUE, FALSE or PARTIALLY FALSE
    String score;

    // The source URL
    String url;

    public PostEntity() {
    }

    public PostEntity(Integer id, String thumbnail, String title, String description, String postDate, String score, String url) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.description = description;
        this.postDate = postDate;
        this.score = score;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String sourceUrl) {
        this.url = sourceUrl;
    }
}
