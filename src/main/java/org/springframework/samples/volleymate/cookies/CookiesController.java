package org.springframework.samples.volleymate.cookies;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CookiesController {

	@GetMapping("/cookies")
	public String showCookiesNotice() {
	    return "cookies";
	}
	
	

}
