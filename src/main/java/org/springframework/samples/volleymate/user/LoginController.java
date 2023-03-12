package org.springframework.samples.volleymate.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@Controller
public class LoginController {

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(){
        return "users/login";
    }

    
    
}
