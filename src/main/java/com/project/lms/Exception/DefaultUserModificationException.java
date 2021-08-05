package com.project.lms.Exception;

public class DefaultUserModificationException extends RuntimeException{
    String message;

    public DefaultUserModificationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
