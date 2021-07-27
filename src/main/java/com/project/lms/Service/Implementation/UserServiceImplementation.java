package com.project.lms.Service.Implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.lms.Entities.Book;
import com.project.lms.Entities.BookRecord;
import com.project.lms.Entities.User;
import com.project.lms.Repository.BookRecordRepository;
import com.project.lms.Repository.UserRepository;
import com.project.lms.Service.UserService;

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

	public User addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActive(true);
		return this.userRepository.save(user);
	}

	@Transactional
	public void deleteUser(String username) {
			this.userRepository.deleteByUsername(username);
	}

	public void updateUser(User user, String username) {
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
		this.userRepository.save(u);
	}
//	Book requests method.


}
