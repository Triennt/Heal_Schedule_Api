package com.asm3.HealScheduleApp.exception;

public class ValueNotMatchException extends RuntimeException{
    public ValueNotMatchException(String message) {
        super(message);
    }

    public ValueNotMatchException(Throwable cause) {
        super(cause);
    }

    public ValueNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
