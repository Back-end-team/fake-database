package com.backend.fakedb.entities;

import javax.persistence.GeneratedValue;

/**
 * Class that stores information which will be sent to the AI module for processing.
 */
public class AiEntity {
    Integer id;
    String title;
    String content;

    public AiEntity() {
    }

    public AiEntity(Integer id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
