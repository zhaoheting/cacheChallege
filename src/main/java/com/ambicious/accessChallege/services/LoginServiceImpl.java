package com.ambicious.accessChallege.services;

import com.ambicious.accessChallege.dao.ProfileDao;
import com.ambicious.accessChallege.models.swaggerModel.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private ProfileDao profileDao;

    @Override
    public Profile getProfile(String userName) {
        return profileDao.queryProfileByName(userName);
    }
}
