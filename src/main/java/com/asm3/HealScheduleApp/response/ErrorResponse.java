package com.asm3.HealScheduleApp.response;

import java.util.List;

public class ErrorResponse extends Response {

    private String descriptions;
    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message) {
        super(status, message);
    }

    public ErrorResponse(int status, String message, String errors) {
        super(status, message);
        this.descriptions = errors;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}
