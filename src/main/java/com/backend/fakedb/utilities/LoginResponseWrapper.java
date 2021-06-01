package com.backend.fakedb.utilities;

public class LoginResponseWrapper {

    // user's id
    int user_id;

    // authentication token
    String token;

    public LoginResponseWrapper() {
    }

    public LoginResponseWrapper(int user_id, String token) {
        this.user_id = user_id;
        this.token = token;
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

    @Override
    public String toString() {
        return "LoginResponseWrapper{" +
                "user_id=" + user_id +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginResponseWrapper that = (LoginResponseWrapper) o;

        if (user_id != that.user_id) return false;
        return token.equals(that.token);
    }

    @Override
    public int hashCode() {
        int result = user_id;
        result = 31 * result + token.hashCode();
        return result;
    }
}
