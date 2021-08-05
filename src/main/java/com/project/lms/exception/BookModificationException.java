package com.project.lms.exception;

public class BookModificationException extends RuntimeException{
    String message;

    public BookModificationException(String message) {
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
