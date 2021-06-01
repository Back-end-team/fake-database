package com.backend.fakedb.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class UserPreferencesEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer userId;

    private Integer providerId;

    // Is the user subscribed to this particular provider?
    boolean subscribed;

    public UserPreferencesEntity() {
    }

    public UserPreferencesEntity(Integer id, Integer userId, Integer providerId, boolean subscribed) {
        this.id = id;
        this.userId = userId;
        this.providerId = providerId;
        this.subscribed = subscribed;
    }

    public UserPreferencesEntity(Integer userId, Integer providerId, boolean subscribed) {
        this.userId = userId;
        this.providerId = providerId;
        this.subscribed = subscribed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}
