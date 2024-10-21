package com.scm.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    //adding contacts to page: handler
    @RequestMapping("/add")
    public  String addContactView(){
        return "user/add_contact";
    }
}
