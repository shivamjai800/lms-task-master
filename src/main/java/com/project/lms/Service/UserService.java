package com.project.lms.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lms.Entities.Book;
import com.project.lms.Entities.BookRecord;
import com.project.lms.Entities.User;
import com.project.lms.Repository.UserRepository;

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
	
}

