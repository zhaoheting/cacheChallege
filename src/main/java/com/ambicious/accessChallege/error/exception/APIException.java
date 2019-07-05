package com.ambicious.accessChallege.error.exception;

import com.ambicious.accessChallege.models.ApiError;

import java.util.ArrayList;
import java.util.List;

public class APIException extends RuntimeException {

    //I don't know its usage.
    private static final long serialVersionUID = 7533687875364006873L;
    protected List<ApiError> apiErrors = new ArrayList<>();

    public APIException(String errorMessage, ApiError apiError) {
        super(errorMessage);

        this.addApiError(apiError);
    }

    public APIException(String errorMessage, List<ApiError> apiErrors) {
        super(errorMessage);
        this.addAllApiErrors(apiErrors);
    }

    public void addApiError(ApiError apiError) {
        this.apiErrors.add(apiError);
    }

    public void addAllApiErrors(List<ApiError> apiErrors) {
        this.apiErrors.addAll(apiErrors);
    }
}
