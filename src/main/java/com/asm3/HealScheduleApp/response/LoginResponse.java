package com.asm3.HealScheduleApp.response;

public class LoginResponse extends Response{
    String userName;
    String token;

    public LoginResponse(int status, String message, String userName, String token) {
        super(status, message);
        this.userName = userName;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
