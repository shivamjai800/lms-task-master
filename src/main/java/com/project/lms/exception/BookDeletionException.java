package com.project.lms.exception;

public class BookDeletionException extends RuntimeException{

    String message;

    public BookDeletionException(String message) {
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
