package com.project.lms.Exception;

public class BookApproveException extends RuntimeException{
    String message;
    String name;

    public BookApproveException() {
    }

    public BookApproveException(String message, String name) {
        this.message = message;
        this.name = name;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
