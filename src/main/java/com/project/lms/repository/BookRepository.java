package com.project.lms.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.lms.entities.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
	
	
	public List<Book> findByTitle(String title);
	
	public List<Book> findByAuthor(String author);
	
	@Query("SELECT b FROM Book b WHERE b.title LIKE %:keyword%  " + " OR b.author LIKE %:keyword%")
//	@Query("SELECT b FROM Book b WHERE b.title LIKE %:keyword%  ")
//	@Query("SELECT b FROM Book b")
	public List<Book> findByKeyword( String keyword);
	
	public void deleteBookById(int id);

	public Book findBookById(int id);

	public List<Book> findAll();


}

