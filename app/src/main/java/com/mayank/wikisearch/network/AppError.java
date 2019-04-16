package com.mayank.wikisearch.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Holds the error code and error message to provide the specific information of the issue.
 * This also contains the constant values of different error codes for mapping.
 * It can also be MAP according to you data layer response
 */

public class AppError {

    public static final String NO_INTERNET = "1001";
    public static final String TIMEOUT = "1002";
    public static final String SOMETHING_WRONG = "1003";

    private String errorCode = "";
    private String errorMessage = "";
    private Class responseClass;

    public AppError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Class getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(Class responseClass) {
        this.responseClass = responseClass;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }
}
