package com.scm.Controllers;

import com.scm.Services.UserServices;
import com.scm.helpers.GetEmail;
import com.scm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {
    @Autowired
    UserServices userServices;
    @ModelAttribute
    void loadLoggedInUsersInUserRoutes(Model model, Authentication authentication){
        if(authentication==null){model.addAttribute("loggedInUser",null); return;}
        String username = GetEmail.getEmailOfLoggedInUser(authentication);
        //If we are logging with GitHub and google we are getting id and if we are logging in by self we are getting email in our protected routes
        // we need to get user by email id
        User user = userServices.getByEmail(username);
        model.addAttribute("loggedInUser",user);
    }
}
