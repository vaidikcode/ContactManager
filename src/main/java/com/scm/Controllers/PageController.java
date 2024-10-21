package com.scm.Controllers;

import com.scm.Forms.UserForm;
import com.scm.Services.UserServices;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Native;

@Controller
public class PageController {
    @Autowired
    UserServices service;
    //Handles home page request and @Request mapping is for general purpose while get mapping is specific request
    @GetMapping("/")
    public String index(){ return "redirect:home"; }
    @RequestMapping("/home")
    public String home(){
        return "home";
    }
    @RequestMapping("/about")
    public String about(){
        return "about";
    }
    @RequestMapping("/services")
    public String services(){
        return "services";
    }
    @RequestMapping("/contact")
    public String contact(){
        return "contact";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/signup")
    public String signup(){
        return "signup";
    }
    @GetMapping("/register")
    public String register(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute("userForm",userForm);
        return "register";
    }
    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session){
        //validating the data
        if(rBindingResult.hasErrors()){
            return "/register";
        }

        //        User user = User.builder()
        //        .name(userForm.getName())
        //        .email(userForm.getEmail())
        //        .password(userForm.getPassword())
        //        .about(userForm.getAbout())
        //        .phoneNumber(userForm.getContactNumber())
        //        .profilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fsignalvnoise.com%2Fposts%2F3104-behind-the-scenes-reinventing-our-default-profile-pictures&psig=AOvVaw16GdhJ8dKEtSe0rFkg5dUm&ust=1727354659895000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJC2vNuP3ogDFQAAAAAdAAAAABAE")
        //        .build();

        //we have converted the data from userForm and stored all in user
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getContactNumber());
        user.setProfilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fsignalvnoise.com%2Fposts%2F3104-behind-the-scenes-reinventing-our-default-profile-pictures&psig=AOvVaw16GdhJ8dKEtSe0rFkg5dUm&ust=1727354659895000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJC2vNuP3ogDFQAAAAAdAAAAABAE");

        User savedUser = service.saveUser(user);

        //creating object of message and sending it through set attribute
        Message message = Message.builder().content("registration successful").type(MessageType.green).build();
        //sending a message
        session.setAttribute("message", message);
        //redirect to register page
        return "redirect:/register";
    }
}
