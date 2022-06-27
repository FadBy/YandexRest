package org.safin.yandex.restapi;

import org.springframework.stereotype.Component;

/**
 * Represents a message for bad request
 */
public class Error {

    private int code;

    private String message;

    public Error(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
