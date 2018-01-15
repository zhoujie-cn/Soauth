package com.soauth.server.openid.connect.authentications.response;

import org.springframework.http.HttpStatus;

public class BaseResponse<T> /*extends ResponseEntity<T>*/{
    private String message;
    private Status status;

    public BaseResponse(HttpStatus statusCode) {
       // super(statusCode);
    }

    public enum Status {
        SUCCESS, ERROR
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}