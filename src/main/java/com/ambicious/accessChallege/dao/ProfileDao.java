package com.ambicious.accessChallege.dao;

import com.ambicious.accessChallege.models.swaggerModel.Profile;

import java.util.List;


public interface ProfileDao {

    List<Profile> queryProfile();

    Profile queryProfileById(int profileId);

    int insertProfile(Profile profile);

    int updateProfile(Profile profile);

    int deleteProfile(int profileId);
}
