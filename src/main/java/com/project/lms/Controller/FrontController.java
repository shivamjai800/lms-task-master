package com.project.lms.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.project.lms.Entities.User;

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
        return "login";
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
//		System.out.println("This function is register in the Front Controller is called");
    	model.addAttribute("user",new User());
        return "register";
    }
    
    @GetMapping("/error")
    public String error()
    {
//		System.out.println("This function is error in the Front Controller is called");
    	return "error";
    }
    

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler()
    {
//		System.out.println("This function is exceptionHandler in the Front Controller is called");
    	return "error";
    }
    
    
    @GetMapping("/test")
    public String test(Model model)
    {
//		System.out.println("This function is test in the Front Controller is called");
    	model.addAttribute("user",new User());
        return "base";
    }
    

}
