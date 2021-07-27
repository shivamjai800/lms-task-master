package com.project.lms.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.project.lms.Entities.Book;
import com.project.lms.Entities.User;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
	
	
	public List<Book> findByTitle(String title);
	
	public List<Book> findByAuthor(String author);
	
	@Query("SELECT b FROM Book b WHERE b.title LIKE %:keyword%  " + " OR b.author LIKE %:keyword%")
	public List<Book> findByKeyword(@Param("keyword") String keyword);
	
	public void deleteBookById(int id);
	
	public Book findById(int id);

	public List<Book> findAll();
}

