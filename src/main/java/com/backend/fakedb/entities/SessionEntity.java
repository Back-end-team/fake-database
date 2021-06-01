package com.backend.fakedb.entities;


import javax.persistence.*;

@Table
@Entity
public class SessionEntity {

    // session id
    @Id
    @GeneratedValue
    int id;

    // user's id for the session
    int user_id;

    // token which the user will send to the back end; used to check the user's authenticity
    String token;

    public SessionEntity() {
    }

    public SessionEntity(int user_id, String token) {
        this.user_id = user_id;
        this.token = token;
    }

    public SessionEntity(int id, int user_id, String token) {
        this.id = id;
        this.user_id = user_id;
        this.token = token;
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionEntity that = (SessionEntity) o;

        if (id != that.id) return false;
        if (user_id != that.user_id) return false;
        return token.equals(that.token);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user_id;
        result = 31 * result + token.hashCode();
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
