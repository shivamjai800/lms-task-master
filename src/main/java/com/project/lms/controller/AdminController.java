package com.project.lms.controller;

import java.util.*;

import com.project.lms.exception.DefaultUserModificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.project.lms.entities.User;
import com.project.lms.service.implementation.UserServiceImplementation;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserServiceImplementation userService;

	@DeleteMapping("/{username}")
	public ResponseEntity<Void> deleteUser(
			@org.springframework.web.bind.annotation.PathVariable("username") String username) {
		
		try {
			userService.deleteUser(username);
		}
		catch(DefaultUserModificationException e)
		{
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
		}
		catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException Exception occurred in delete user of Admin COntroller");
			System.out.println("You might have passed null Username value in user Service method method");
			System.out.println("Actual message = "+e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@GetMapping("")
	public String dashboard(ModelAndView model) {

		return "admin/dashboard";
	}

	@PostMapping("/search")
	public String search(@RequestParam("keyword") String keyword, Model model) {
		List<User> list = this.userService.listByKeyword(keyword);
		List<User> users = new ArrayList<User>();
		try
		{
			for (User u : list) {
				User user = new User();
				user.setName(u.getName());
				user.setUsername(u.getUsername());
				user.setRoles(u.getRoles());
				users.add(user);
			}
		}
		catch(Exception e)
		{
			System.out.println(" Exception occurred in search user of Admin COntroller");
			System.out.println("Actual message = "+e.getMessage());
		}

		model.addAttribute("users", users);
		return "admin/dashboard";
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.PUT, consumes = "*/*")
	public ResponseEntity<Void> updateUser(@RequestBody User user,
			@org.springframework.web.bind.annotation.PathVariable("username") String username) {

		try {
			userService.updateUser(user, username);
		}
		catch(DefaultUserModificationException e)
		{
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
		}
		catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException Exception occurred in update user of Admin COntroller");
			System.out.println("You might have passed null Username value in user Service method method");
			System.out.println("Actual message = "+e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		catch(NullPointerException e)
		{
			System.out.println("NullPointer Exception occurred in update user of Admin COntroller");
			System.out.println("You might have got the null user from the update user method in the service class");
			System.out.println("Actual message = "+e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

//  ----------------	 Book Controller 

}
