package com.ambicious.accessChallege.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class ApiError implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("errorCode")
    private String errorCode = null;

    @JsonProperty("devMessage")
    private String devMessage = null;

    @JsonProperty("userMessage")
    private String userMessage = null;

    @JsonProperty("field")
    private String field = null;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    /**
     * To compare the content of two objects.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        //verify they are the same obj.
        if (this == obj) {
            return true;
        }
        //verify if the parameter obj is null and the two objs don't belong to the same class.
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        //verify every property of the parameter obj is same.
        ApiError apiError = (ApiError) obj;
        return Objects.equals(this.devMessage, apiError.devMessage)
                && Objects.equals(this.errorCode, apiError.errorCode)
                && Objects.equals(this.field, apiError.field)
                && Objects.equals(this.userMessage, apiError.userMessage);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("class ApiError {\n");
        stringBuilder.append("errorCode:").append(errorCode).append("\n");
        stringBuilder.append("errorCode:").append(errorCode).append("\n");
        stringBuilder.append("errorCode:").append(errorCode).append("\n");
        stringBuilder.append("errorCode:").append(errorCode).append("\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, devMessage, userMessage, field);
    }
}
