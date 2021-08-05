package com.project.lms.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.project.lms.entities.User;

@Service("UserService")
public interface UserService{
	
	
	public List<User> getAllUser();
	public User getTheUser(String username);
	public User addUser(User user);
	public void deleteUser(String username);
	public void updateUser(User user, String username);
	public List<User> listByKeyword(String keyword);
	public void updateUserPassword(User user);
	public boolean passwordMatch(String password, String hash);


	/* Book Request */
	public void removeBookRecordRequest(String username, int bookId);
}

