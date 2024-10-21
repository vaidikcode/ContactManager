package com.scm.Controllers;

import com.scm.Services.UserServices;
import com.scm.helpers.GetEmail;
import com.scm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServices userServices;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    //all are user related page controlled which should only be accessed by login user
    //we are going to create secure set and pages and with controller which only user can access or someone who is logged in can only access

    //User Dashboard
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "/user/dashboard";
    }

    //User Profile
    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {

        return "/user/profile";
    }
}
