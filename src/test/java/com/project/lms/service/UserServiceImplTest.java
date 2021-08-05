package com.project.lms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.project.lms.entities.BookRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


import com.project.lms.entities.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.project.lms.repository.UserRepository;
import com.project.lms.service.implementation.UserServiceImplementation;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private UserServiceImplementation userService;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	private User user = new User();
	@Before
	public void before()
	{
		user.setName("shivam");
		user.setUsername("shivamuser");
		user.setPassword("abcdef");
		user.setRoles("ROLE_USER");
	}

	@Test
	public void getAllUser()
	{
		List<User> list = new ArrayList<>();
		list.add(user);
		Mockito.when(this.userRepository.findAll()).thenReturn(list);
		Assert.assertEquals(list,this.userService.getAllUser());
	}

	@Test
	public void getTheUser()
	{
		Mockito.when(userRepository.findByUsername("shivamuser")).thenReturn(user);
		assertThat(userService.getTheUser("shivamuser")).isEqualTo(user);
	}

	@Test
	public void addUser()
	{
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Mockito.when(userRepository.save(null)).thenThrow(IllegalArgumentException.class);

		User user1  = userService.addUser(user);
		assertTrue(user1.getName().equals(user.getName()));
		assertTrue(user1.getUsername().equals(user.getUsername()));  
		assertTrue(user1.isActive());
		
		NullPointerException exception = Assert.assertThrows( NullPointerException.class , () -> {userService.addUser(null);});
		String expectedMessage = "";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		
	}



	@Test
	public void deleteTheUser()
	{
		Mockito.doThrow(new IllegalArgumentException("Null object deleted")).when(userRepository).deleteByUsername(null);
		Mockito.doNothing().when(userRepository).deleteByUsername("shivamuser");
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> {userService.deleteUser(null);});
		String expectedMessage = "Null";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

		userRepository.deleteByUsername("shivamuser");
		Mockito.verify(userRepository, Mockito.times(1)).deleteByUsername("shivamuser");
	}

	@Test
	public void updateUser()
	{
		Mockito.when(userRepository.findByUsername("shivamuser")).thenReturn(user);
		Mockito.when(userRepository.findByUsername(null)).thenThrow(IllegalArgumentException.class);
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Mockito.when(userRepository.save(null)).thenThrow(IllegalArgumentException.class);
		User user1 = new User();
		user1.setName("shivamuser1");
		user1.setRoles("ROLE_ADMIN");
		userService.updateUser(user1,"shivamuser");
		assertTrue(user.getRoles().equals("ROLE_ADMIN"));
		assertThat(user.getName()).isEqualTo("shivamuser1");
	}

	@Test
	public void ListByKeyword()
	{
		List<User> list = new ArrayList<>();
		List<String> names = new ArrayList<String>(Arrays.asList("Ab","bc","cd","de"));

		names.forEach(e->{
			User u = new User();
			u.setName(e);
			list.add(u);
		});

		Mockito.when(userRepository.findAll()).thenReturn(list);
//		Mockito.when(userRepository.search("shivam")).thenReturn(user);

		assertThat(userService.listByKeyword(null)).isEqualTo(list);
//		assertThat(userService.listByKeyword("shivam")).isEqualTo(user);


	}

	@Test
	public void removeBookRecordRequest()
	{
		Mockito.when(this.userRepository.findByUsername("shivamuser")).thenReturn(user);
		BookRecord bookRecord = new BookRecord(); bookRecord.setBookId(1); bookRecord.setStatus("REQUESTED");
		List<BookRecord> list = new ArrayList<BookRecord>(); list.add(bookRecord);
		user.setRequest(list);

		this.userService.removeBookRecordRequest("shivamuser",1);
		assertTrue(user.getRequest().isEmpty());
	}

}
