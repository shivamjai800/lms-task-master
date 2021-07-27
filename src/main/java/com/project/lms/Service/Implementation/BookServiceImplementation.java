package com.project.lms.Service.Implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lms.Entities.Book;
import com.project.lms.Entities.UnitBook;
import com.project.lms.Entities.User;
import com.project.lms.Repository.BookRepository;
import com.project.lms.Service.BookService;

@Service
public class BookServiceImplementation implements BookService {

	@Autowired
	BookRepository bookRepository;
	
	
	@Override
	public Book getBookById(int id)
	{
		return this.bookRepository.findById(id);
	}
	
	@Override
	public List<Book> getAllBooks() {
		List<Book> list = (List<Book>) bookRepository.findAll();
		return list;
	}

	@Override
	public List<Book> getTheBookByTitle(String title) {
		
		return this.bookRepository.findByTitle(title);
	}

	@Override
	public List<Book> getTheBookByAuthor(String author) {
		return this.bookRepository.findByTitle(author);
	}

	@Override
	public Book addBook(Book book) {
		
		book = this.bookRepository.save(book);
		for(int i=1;i<=book.getQuantity();i++)
		{
			UnitBook unitBook = new UnitBook();
			unitBook.setId(book.getId()+" "+i);
			unitBook.setAssigned(false);
			book.getUnitBooks().add(unitBook);
		}
		return this.bookRepository.save(book);
	}

	@Override
	@Transactional
	public void deleteBookById(int id) {
		this.bookRepository.deleteBookById(id);

	}

	@Override
	public void updateBookById(Book book, int id) {
		Book b = this.bookRepository.findById(id);
		if(book.getId()!=id) return;
		else
		{
			b.setAuthor(book.getAuthor());
			b.setQuantity(book.getQuantity());
			b.setTitle(book.getTitle());
			this.bookRepository.save(b);
		}
	}

	@Override
	public List<Book> listByKeyword(String keyword) {

		if(keyword==null)
			return this.bookRepository.findAll();
		return this.bookRepository.findByKeyword(keyword);
	}

	@Override
	public void removeBookRecordRequest(int bookId) {
		Book b = this.bookRepository.findById(bookId);
		b.getRequest().removeIf(e -> e.getBookId()==bookId && e.getStatus().equals("REQUESTED"));
		this.bookRepository.save(b);
		return;
	}

	@Override
	public int getAvailability(int id) {
		
		return 0;
	}

	@Override
	public List<String> getIssuedUsers(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
