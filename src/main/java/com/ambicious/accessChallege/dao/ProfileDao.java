package com.ambicious.accessChallege.dao;

import com.ambicious.accessChallege.models.swaggerModel.Profile;

import java.util.List;


public interface ProfileDao {

//    List<Profile> queryProfile();

    Profile queryProfileByName(String userName);

//    int insertProfile(Profile profile);
//
//    int updateProfile(Profile profile);
//
//    int deleteProfile(int profileId);
}
