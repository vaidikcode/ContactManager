package com.scm.helpers;

import com.scm.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;

public class GetEmail {
    //if of Google and GitHub is passed to the secure user profile rout using we need to get email of user
    public static String getEmailOfLoggedInUser(Authentication authentication){
        //we need to get email from GitHub and google But it is only providing us with id So we need to get email

        //OAuthAuthorizationTokens allow us to access the user related details that the oauth client has to provide through provider
        var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
        String authorizedClientRegistrationId=oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        if (authentication instanceof OAuth2AuthenticationToken){
            String email="";
            //for Google
            var oAuth2User = (OAuth2User)authentication.getPrincipal();
            if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                email = oAuth2User.getAttribute("email").toString();
            }
            //for Google
            else{
                email = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email").toString() : oAuth2User.getAttribute("login").toString()+"@gmail.com";
            }
            return email;
        }
        else {
            return authentication.getName();
        }
    }
}
