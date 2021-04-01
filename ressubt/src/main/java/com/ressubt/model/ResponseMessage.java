package com.ressubt.model;

public class ResponseMessage implements Model {

    private int code;
    private String message;

    public ResponseMessage(String message) {
	this(-1, message);
    }

    public ResponseMessage(int code, String message) {
	this.code = code;
	this.message = message;
    }

    public int getCode() {
	return code;
    }

    public String getMessage() {
	return message;
    }

}
