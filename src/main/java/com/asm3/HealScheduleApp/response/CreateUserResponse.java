package com.asm3.HealScheduleApp.response;

import com.asm3.HealScheduleApp.entity.User;

public class CreateUserResponse extends Response {
    private User user;

    public CreateUserResponse(int status, String message, User user) {
        super(status, message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
