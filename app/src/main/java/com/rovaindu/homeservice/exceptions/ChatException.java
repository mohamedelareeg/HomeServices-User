package com.rovaindu.homeservice.exceptions;

public class ChatException extends Exception {
    private String code;
    private String details;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ChatException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public ChatException(String code, String message, String details) {
        super(message);
        this.setCode(code);
        this.setDetails(details);
    }
}