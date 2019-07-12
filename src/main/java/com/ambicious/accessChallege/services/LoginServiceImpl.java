package com.ambicious.accessChallege.services;

import com.ambicious.accessChallege.models.swaggerModel.Profile;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public Profile login(String username) {
        if (username != null) {
//            getProfile();
        }

        return null;
    }
}
