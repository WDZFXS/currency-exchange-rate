package com.wdzfxs.currencyexchangerate.client.handler;

public class ErrorMessage {

    private static final String INTERNAL_SERVER_ERROR = "Internal server error";

    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String internalServerError() {
        return INTERNAL_SERVER_ERROR;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}