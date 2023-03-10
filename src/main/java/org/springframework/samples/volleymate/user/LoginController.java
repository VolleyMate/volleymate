package org.springframework.samples.volleymate.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "users/login";
    }

    
    
}
