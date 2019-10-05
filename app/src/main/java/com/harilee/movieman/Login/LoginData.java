package com.harilee.movieman.Login;

public class LoginData {

    String username;
    String password;

    public LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginData() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
