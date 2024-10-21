package com.scm.ConfigFiles;

import com.scm.Repostiries.UserRepo;
import com.scm.Services.UserServices;
import com.scm.helpers.ApplicationsConstant;
import com.scm.model.Providers;
import com.scm.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserRepo userRepo;

    Logger logger = LoggerFactory.getLogger((OAuthAuthenticationSuccessHandler.class));

    //We are going to handle all the methods related when you log in with Google like taking data from Google of email and even service provider
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("OAuthAuthenticationSuccessHandler");
        //We need to first know which authentication provider we are using by getting client id
        var OAuth2AuthenticationToken = (org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken)authentication;
        String authorizedClientRegistrationId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        //using if statements to execute different commands for different providers
        var oAuth2User = (DefaultOAuth2User)authentication.getPrincipal();
        //We need to know what are we getting from oauth2 client by provider So we have to log each key value attribute pair

        oAuth2User.getAttributes().forEach((key,value)->{
            logger.info(key + " : " + value);
        });
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(ApplicationsConstant.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("password");


        if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
            user.setEmail(oAuth2User.getAttribute("email").toString());
            user.setProfilePic(oAuth2User.getAttribute("picture").toString());
            user.setName(oAuth2User.getAttribute("name").toString());
            user.setProviderUserId(oAuth2User.getName());
            user.setProviders(Providers.GOOGLE);
            user.setAbout("->Google");
        }
        if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            String email = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email").toString() : oAuth2User.getAttribute("login").toString()+"@gmail.com";
            String picture = oAuth2User.getAttribute("avatar_url").toString();
            String name = oAuth2User.getAttribute("login").toString();
            String providerUserId = oAuth2User.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProviders(Providers.GITHUB);
            user.setAbout("->Github");
        }
      /*  DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
       String name = user.getAttribute("name").toString();
       String email = user.getAttribute("email").toString();
       String profilePic = user.getAttribute("picture").toString();
        //setting up all the data which is to be saved in user repo by creating object of user1 and checking if it already exists in DB
        User user1 =  new User();
        user1.setName(name);
        user1.setEmail(email);
        user1.setPassword("password");
        user1.setProfilePic(profilePic);
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProviders(Providers.GOOGLE);
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderUserId(user.getName());
        user1.setRoleList(List.of(ApplicationsConstant.ROLE_USER));
        //now we need to se if the user we have created n database exists or not
        //if it is null then we save the user otherwise if it exists then we don't have to do anything
        User userInDb = userRepo.findByEmail(email).orElse(null);
        if(userInDb==null){
            userRepo.save(user1);}
    */


        User userInDb = userRepo.findByEmail(user.getEmail()).orElse(null);
        if(userInDb==null){
            userRepo.save(user);}

        //redirecting to profile page
        new DefaultRedirectStrategy().sendRedirect(request, response,"/user/profile");
    }
}
