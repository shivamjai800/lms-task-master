package com.project.lms.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.project.lms.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	public List<User> findByName(String name);

	public List<User> findAll();

	public User findByUsername(String username);

	public void deleteByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.name LIKE %:keyword%" + " OR u.username LIKE %:keyword%" + " OR u.roles LIKE %:keyword%")
	public List<User> search(String keyword);
}
