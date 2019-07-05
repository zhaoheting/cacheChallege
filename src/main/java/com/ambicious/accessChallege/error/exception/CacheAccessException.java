package com.ambicious.accessChallege.error.exception;

import com.ambicious.accessChallege.models.ApiError;

import java.util.List;

public class CacheAccessException extends APIException {

    private static final long serialVersionUID = 7596014398012651365L;

    public CacheAccessException(String errorMessage, ApiError apiError) {
        super(errorMessage, apiError);
    }

    public CacheAccessException(String errorMessage, List<ApiError> apiErrors) {
        super(errorMessage, apiErrors);
    }
}