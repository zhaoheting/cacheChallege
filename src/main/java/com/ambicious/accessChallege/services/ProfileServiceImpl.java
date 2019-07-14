package com.ambicious.accessChallege.services;

import com.ambicious.accessChallege.config.CacheAccess;
import com.ambicious.accessChallege.dao.ProfileDao;
import com.ambicious.accessChallege.models.swaggerModel.Profile;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDao profileDao;
    @Autowired
    private ObjectAccessService objectAccessService;
    @Autowired
    private CacheAccess cacheAccess;

    @Override
    public Profile getProfile(String userName) throws IOException {
        Profile profile;
        if (cacheAccess.containKey(userName)) {
            profile = objectAccessService.get(userName, Profile.class);
        } else {
            profile = profileDao.queryProfileByName(userName);
        }
        return profile;

    }

    @Override
    public void addProfile(Profile profile) throws JsonProcessingException {

        Profile profileAdded = new Profile();
        profileAdded.setUserName(profile.getUserName());
        profileAdded.setAge(profile.getAge());
        profileAdded.setCreateTime(new Date());
        profileAdded.setLastEditTime(new Date());
        profileDao.insertProfile(profile);
        objectAccessService.saveAsync(profileAdded.getUserName(), profileAdded);
    }
}
