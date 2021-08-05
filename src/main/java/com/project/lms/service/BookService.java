package com.project.lms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lms.entities.Book;
import org.springframework.web.multipart.MultipartFile;

@Service("BookService")
public interface BookService {
	
	public Book getBookById(int id);
	public List<Book> getAllBooks();
	public List<Book> getTheBookByTitle(String title);
	public List<Book> getTheBookByAuthor(String author);
	public Book addBook(Book book, MultipartFile bookImage);
	public void deleteBookById(int id);
	public void updateBookById(Book book, int id);
	public List<Book> listByKeyword(String keyword);
	public void removeBookRecordRequest(int bookId, String username);

	public int getAvailability(int id);
	public List<String> getIssuedUsers(int id);
	
}
