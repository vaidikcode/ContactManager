package com.scm.ConfigFiles;

import com.scm.Services.SecurityCustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private SecurityCustomUserDetailService UserDetailService;
    @Autowired
    private OAuthAuthenticationSuccessHandler handler;
    //configuration of authentication provider for spring security
    @Bean
    public AuthenticationProvider authenticationProvider(){

        //We need to encode the password and take it from database too instead of hardcoding it
        //encoding of password is necessary to prevent casual observation in server documents
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //userDetailsService can be used to interact with database
        daoAuthenticationProvider.setUserDetailsService(UserDetailService );
        //password encoder ka object pass krne k liye method bnaya he
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    //Creating this method to allow aces to different users to different routes
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //urls configure kia he konse public or konse access based rhenge
        httpSecurity.authorizeHttpRequests(authorise -> {
            authorise.requestMatchers("/user/**").authenticated();
            authorise.anyRequest().permitAll();
        });
        //form default login if we have to do anything related to log in form we will do it here
        httpSecurity.formLogin(formLogin ->{
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/dashboard");
            formLogin.failureForwardUrl("/login?error=true");
            //related to work of naming field
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(formLogout ->{
           formLogout.logoutUrl("/do-logout");
           formLogout.logoutSuccessUrl("/login?logout=true");
        });
        //login with oauth2 on Google
        httpSecurity.oauth2Login(aouth2-> {
            aouth2.loginPage("/login");
            aouth2.successHandler(handler);
        });
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
