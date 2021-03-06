package com.project.lms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.security.Principal;

import com.project.lms.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.project.lms.entities.User;
import com.project.lms.service.implementation.UserServiceImplementation;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserServiceImplementation userService;
	@Autowired
	DaoAuthenticationProvider authenticationManager;

	@GetMapping("/edit")
	public String userEditing(Model model, Principal principal) {
		User user;
		try
		{
			String username = principal.getName();
			user = this.userService.getTheUser(username);
			model.addAttribute("user", user);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("IIlegalArgument Exception occurred in editing of User COntroller");
			System.out.println("You might have passed the null username");
			System.out.println("Actual message = "+e.getMessage());
		}
		catch (NullPointerException e) {
			System.out.println("IIlegalArgument Exception occurred in editing of User COntroller");
			System.out.println("The user might not be logged in, Please check");
			System.out.println("Actual message = " + e.getMessage());
		}
		return "user/userEditing";
	}
	

	@PostMapping("")
	public String addUser(HttpServletRequest request, @Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(e -> {
				System.out.println("ERROR  = "+e.toString());
			});
			model.addAttribute("user", user);
			return "register";
		}

		try {
			String username = user.getUsername();
			String password = user.getPassword();
			User user1 = userService.addUser(user);

//			Authenticate
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
			authToken.setDetails(new WebAuthenticationDetails(request));
			Authentication authentication = authenticationManager.authenticate(authToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}
		catch (UserAlreadyExistsException e)
		{
			System.out.println("UserAlreadyExistsException = "+e.getMessage());
			model.addAttribute("user",user);
			FieldError fieldError = new FieldError("user","username",e.getMessage());
			result.addError(fieldError);
			return "register";
		}
		catch(NullPointerException | IllegalArgumentException e)
		{
			String message ="";
			if(e.getClass().equals(NullPointerException.class)) message.concat("Null Pointer Exception");
			else message.concat(" Trying to save the null Object to the class");
			System.out.println(e.getClass() +" Ocuured in addUser method in User COntroller");
			System.out.println(message);
			System.out.println("Actual Message"+ e.getMessage());
			return "error";
		}
		return "redirect:/user";
//		if (user.getRoles().equals("ROLE_ADMIN"))
//			return "admin/dashboard";
//		else
//			return "user/dashboard";
	}

	@GetMapping("")
	public String dashboard(ModelAndView model) {
		return "user/dashboard";
	}

	@DeleteMapping("/{username}")
	public String deleteUser(@PathVariable("username") String username) {

		try {
			userService.deleteUser(username);
		} catch (IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException Exception occurred in delete user of User COntroller");
			System.out.println("You might have passed null Username value in user Service method method");
			System.out.println("Actual message = "+e.getMessage());
			return "";
		}
		return "";

	}

	@RequestMapping(value = "/{username}", method = RequestMethod.PUT, consumes = "*/*")
	public ResponseEntity<Void> updateUser(@Valid @RequestBody User user,
			@org.springframework.web.bind.annotation.PathVariable("username") String username, Principal principal) {

		if (principal == null || !username.equals(principal.getName())) {

			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
		}

		try {
			User u = this.userService.getTheUser(principal.getName());
			userService.updateUser(user, username);
			if (!this.userService.passwordMatch(user.getPassword(), u.getPassword())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}
		catch (NullPointerException e)
		{
			System.out.println("Null Pointer Exception occurred in update user of User COntroller");
			System.out.println("You might have not got null username or Your principal is null");
			System.out.println("Actual message = "+e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("Null Pointer Exception occurred in update user of User COntroller");
			System.out.println("You might have passed null value in password encoder method");
			System.out.println("Actual message = "+e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
