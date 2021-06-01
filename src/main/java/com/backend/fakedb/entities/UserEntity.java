package com.backend.fakedb.entities;

import javax.persistence.*;

@Entity
@Table
public class UserEntity {

    // The associated ID
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    Integer id;

    // The username of this user
    String username;

    // A hash for the associated password
    String passwordHash;

    // A URL to the user's avatar picture.
    String avatarUrl;

    // A short bio describing the user
    String bio;

    // The associated email
    String email;

    public UserEntity() {
    }

    public UserEntity(Integer id, String username, String passwordHash, String avatarUrl, String bio, String email) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (!id.equals(that.id)) return false;
        if (!username.equals(that.username)) return false;
        if (!passwordHash.equals(that.passwordHash)) return false;
        if (!avatarUrl.equals(that.avatarUrl)) return false;
        if (!bio.equals(that.bio)) return false;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + passwordHash.hashCode();
        result = 31 * result + avatarUrl.hashCode();
        result = 31 * result + bio.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
