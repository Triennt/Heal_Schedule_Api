package com.asm3.HealScheduleApp.response;

public class LoginResponse extends Response{
    String token;

    public LoginResponse(int status, String message, String token) {
        super(status, message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
