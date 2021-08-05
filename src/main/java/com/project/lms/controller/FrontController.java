package com.project.lms.controller;

import com.project.lms.details.CustomUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.lms.entities.User;

@Controller
public class FrontController {

	@GetMapping("/")
	public String nothing()
    {
//        System.out.println("THe function nothing in the front controller is called");
		
        return "welcome";
    }
	@PostMapping("/dologin")
	public String dologin()
	{
//		System.out.println("This function is do login in the Front Controller is called");
		return "welcome";
	}
	
    @GetMapping("/welcome")
    public String about()
    {
//		System.out.println("This function is about in the Front Controller is called");
        System.out.println("welcome");
        return "welcome";
    }

    @GetMapping("/login")
    public String login()
    {
//		System.out.println("This function is login in the Front Controller is called");
        String redirectURL;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            redirectURL = "login";
        }
        else
        {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            if(userDetails.getRoles().equals("ROLE_ADMIN"))
            {
                redirectURL = "redirect:/admin";
            }
            else
                redirectURL = "redirect:/user";
        }
        return redirectURL;
    }
    
    @GetMapping("/logout")
    public String logout()
    {
//		System.out.println("This function is logout in the Front Controller is called");
        return "logout";
    }

    @GetMapping("/register")
    public String register(Model model)
    {
    	model.addAttribute("user",new User());
        return "register";
    }




    

}
