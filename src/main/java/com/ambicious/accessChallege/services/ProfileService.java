package com.ambicious.accessChallege.services;

import com.ambicious.accessChallege.models.swaggerModel.Profile;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface ProfileService {

    Profile getProfile(String userName) throws IOException;
    void addProfile(Profile profile) throws JsonProcessingException;
}
