package com.ambicious.accessChallege.services;

import com.ambicious.accessChallege.models.swaggerModel.Profile;

@FunctionalInterface
public interface LoginService {

    Profile login(String username);
}
