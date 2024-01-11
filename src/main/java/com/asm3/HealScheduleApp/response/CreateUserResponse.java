package com.asm3.HealScheduleApp.response;

public class CreateUserResponse extends Response {
    private Object data;

    public CreateUserResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
