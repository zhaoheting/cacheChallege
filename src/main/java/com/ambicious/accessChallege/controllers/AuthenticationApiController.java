package com.ambicious.accessChallege.controllers;

import com.ambicious.accessChallege.models.swaggerModel.Profile;
import com.ambicious.accessChallege.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationApiController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public Profile login(String userName){
        return loginService.login(userName);
    }
}
