package com.ambicious.accessChallege.controllers;

import com.ambicious.accessChallege.models.swaggerModel.Profile;
import com.ambicious.accessChallege.services.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationApiController {

    @Autowired
    private ProfileService profileService;

    @RequestMapping(value = "/getProfile", method = RequestMethod.GET)
    public Profile login(String userName) throws IOException {

        return profileService.getProfile(userName);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void registerProfile(Profile profile) throws JsonProcessingException {
        //profileValidator.validate(profile);
        profileService.addProfile(profile);
    }
}
