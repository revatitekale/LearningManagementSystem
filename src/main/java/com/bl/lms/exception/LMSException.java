package com.bl.lms.exception;

public class LMSException extends RuntimeException {

    private static final long serialVersionUID = 5776681206288518465L;

    private exceptionType type;
    private String message;

    public LMSException(exceptionType type, String message) {
        super(message);
        this.type = type;
        this.message=message;
    }

    public enum exceptionType {
        USER_NOT_FOUND,
        INVALID_EMAIL,
        INCORRECT_PASSWORD,
        INVALID_TOKEN,
        DATA_NOT_FOUND,
        INVALID_ID;
    }
}
