package com.project.lms.Exception;

public class UserAlreadyExistsException extends RuntimeException{

    String message;

    public UserAlreadyExistsException(String message) {
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
