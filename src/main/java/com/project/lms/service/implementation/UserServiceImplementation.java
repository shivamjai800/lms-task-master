package com.project.lms.service.implementation;

import java.util.List;

import javax.transaction.Transactional;

import com.project.lms.exception.DefaultUserModificationException;
import com.project.lms.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.lms.entities.User;
import com.project.lms.repository.BookRecordRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.service.UserService;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	BookRecordRepository bookRequestRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	BookServiceImplementation bookService = new BookServiceImplementation();

	public List<User> getAllUser() {
		List<User> list = (List<User>) userRepository.findAll();
		return list;
	}

	public User getTheUser(String username) {
		return this.userRepository.findByUsername(username);
	}

	public User addUser(User user) throws UserAlreadyExistsException {

		User user1 = this.userRepository.findByUsername(user.getUsername());
		if(user1!=null)
			throw new UserAlreadyExistsException("User with username already exists please change the username");
		user.setRoles("ROLE_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActive(true);
		return this.userRepository.save(user);
	}

	@Transactional
	public void deleteUser(String username) throws DefaultUserModificationException{

		if(username.equals("admin"))
			throw new DefaultUserModificationException("Default User deletion not allowed");

			this.userRepository.deleteByUsername(username);
	}

	public void updateUser(User user, String username) throws DefaultUserModificationException {

		if(username.equals("admin"))
			throw new DefaultUserModificationException("Default user cannot be changed");
		User u = this.userRepository.findByUsername(username);
		u.setName(user.getName());
		u.setRoles(user.getRoles());
		this.userRepository.save(u);
	}

	public List<User> listByKeyword(String keyword) {
		if (keyword != null) {
			return this.userRepository.search(keyword);
		}
		return this.userRepository.findAll();
	}

	public void updateUserPassword(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.userRepository.save(user);
	}

	public boolean passwordMatch(String password, String hash) {
		return this.passwordEncoder.matches(password, hash);
	}

	@Override
	public void removeBookRecordRequest(String username, int bookId) {
		User u = this.userRepository.findByUsername(username);
		u.getRequest().removeIf(e-> e.getBookId()==bookId && e.getStatus().equals("REQUESTED"));
	}
//	Book requests method.


}
