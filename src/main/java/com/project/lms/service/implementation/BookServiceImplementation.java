package com.project.lms.service.implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.transaction.Transactional;

import com.project.lms.exception.BookDeletionException;
import com.project.lms.exception.BookModificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.project.lms.entities.Book;
import com.project.lms.entities.UnitBook;
import com.project.lms.repository.BookRepository;
import com.project.lms.service.BookService;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookServiceImplementation implements BookService {

	@Autowired
	BookRepository bookRepository;
	
	
	@Override
	public Book getBookById(int id)
	{
		return this.bookRepository.findBookById(id);
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
	public Book addBook(Book book, MultipartFile bookImage) {
//		System.out.println("Insid e the add book of the service Implementation");

		book = this.bookRepository.save(book);
		if(!bookImage.isEmpty())
		{
			try {
				book.setImage("BookImage"+book.getId());
				File saveImage = new ClassPathResource("static/image").getFile();

//				System.out.println(saveImage.getAbsolutePath()+"  ");;
				Path path = Paths.get(saveImage.getAbsolutePath()+File.separator+book.getImage());
				Files.copy(bookImage.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
//				System.out.println("File is saved");
			}
			catch (IOException e)
			{
				System.out.println("Add book service implementation in service class");
				System.out.println(e.getMessage());
			}

		}
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
	public void deleteBookById(int id) throws BookDeletionException {

		Book book = this.bookRepository.findBookById(id);
		if(book==null)
			throw new BookDeletionException("Book Not Found");

		book.getUnitBooks().forEach(e->{
			if(e.isAssigned()==true)
				throw new BookDeletionException("Unit Book Assigned cannot be Deleted");
		});


		this.bookRepository.deleteBookById(id);

	}

	@Override
	public void updateBookById(Book book, int id) throws BookModificationException {
		Book b = this.bookRepository.findBookById(id);
		if(book.getId()!=id) return;
		else
		{
			long count = b.getUnitBooks().stream().filter(e-> e.isAssigned()).count();

			if(count> book.getQuantity())
			{
				throw new BookModificationException("The Quantity cannot be Reduced as Books are assigned");
			}

			if(b.getQuantity()< book.getQuantity())
			{
				for(int i=b.getQuantity()+1;i< book.getQuantity();i++)
				{
					UnitBook unitBook = new UnitBook();
					unitBook.setId(book.getId()+" "+i);
					unitBook.setAssigned(false);
					book.getUnitBooks().add(unitBook);
				}
			}
			else if(b.getQuantity()> book.getQuantity())
			{
				throw new BookModificationException("Currently Book Size cannot be decreased");
			}

			b.setAuthor(book.getAuthor());
			b.setQuantity(book.getQuantity());
			b.setTitle(book.getTitle());
			this.bookRepository.save(b);
		}
	}

	@Override
	public List<Book> listByKeyword(String keyword) {

		if(keyword==null || keyword.equals(""))
			return this.bookRepository.findAll();
		return this.bookRepository.findByKeyword(keyword);
	}

	@Override
	public void removeBookRecordRequest(int bookId, String username) {
		Book b = this.bookRepository.findBookById(bookId);
		b.getRequest().removeIf(e -> e.getBookId()==bookId && e.getStatus().equals("REQUESTED") && e.getUserUsername().equals(username));
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
