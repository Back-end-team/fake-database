package com.backend.fakedb.entities;

import java.sql.Date;
import java.util.UUID;

/**
 * Entity representing the information coming from the Ingestion System
 */
public class IngestionEntity {
    UUID id;

    String title;

    String thumbnail;

    String description;

    String postDate;

    String url;

    public IngestionEntity() {
    }

    public IngestionEntity(UUID id, String title, String thumbnail, String description, String postDate, String url) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.postDate = postDate;
        this.url = url;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
