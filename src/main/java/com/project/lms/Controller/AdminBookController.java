package com.project.lms.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.*;
import com.project.lms.Entities.Book;
import com.project.lms.Entities.BookRecord;
import com.project.lms.Entities.UnitBook;
import com.project.lms.Entities.User;
import com.project.lms.Service.Implementation.BookRecordServiceImpl;
import com.project.lms.Service.Implementation.BookServiceImplementation;
import com.project.lms.Service.Implementation.UserServiceImplementation;
import java.lang.reflect.Field;

@Controller
@RequestMapping("/admin")
public class AdminBookController {

	@Autowired
	BookServiceImplementation bookService;

	@Autowired
	BookRecordServiceImpl bookRecordService;

	@GetMapping("/books/addBook")
	public String addBookView(Model model) {
		model.addAttribute("book", new Book());
		return "admin/addBookView";
	}

	@PostMapping("/book")
	public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model) {
		System.out.println(book);
		if (result.hasErrors()) {
			model.addAttribute("book", book);
			return "admin/addBookView";
		}
		try {
			book = bookService.addBook(book);

		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException Exception occurred in addBook of Admin Book Controller");
			System.out.println("Book Passed is either null");
			System.out.println("Actual message = " + e.getMessage());
		}
		return "admin/dashboard";
	}

	@PostMapping("/books/search")
	public String search(@RequestParam("keyword") String keyword, Model model) {

		List<Book> books = new ArrayList<Book>();
		try {
			books = this.bookService.listByKeyword(keyword);
		}
		catch (Exception e)
		{
			System.out.println("Exception occurred in search of Admin Book Controller");
			System.out.println("Book Passed is either null");
			System.out.println("Actual message = " + e.getMessage());
		}

		model.addAttribute("books", books);
		return "admin/allBooks";
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<Void> deleteBook(@org.springframework.web.bind.annotation.PathVariable("id") int id) {
		try {

			this.bookService.deleteBookById(id);
		} catch (IllegalArgumentException e) {
			System.out.println(" IllegalArgumentException Exception occurred in delete Book of Admin Book Controller");
			System.out.println("Book Passed is either null");
			System.out.println("Actual message = " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@RequestMapping(value = "/books/{id}", method = RequestMethod.PUT, consumes = "*/*")
	public ResponseEntity<Void> updateUser(@RequestBody Book book,
			@org.springframework.web.bind.annotation.PathVariable("id") int id) {

		try {
			this.bookService.updateBookById(book, id);
		} catch (NullPointerException e) {
			System.out.println(" NullPointer Exception occurred in update Book of Admin Book Controller");
			System.out.println("Book object has null value");
			System.out.println("Actual message = " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(" Illegal Exception occurred in update Book of Admin Book Controller");
			System.out.println("id passed to the object is null");
			System.out.println("Actual message = " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

//  Approving the request related to the book.
	@GetMapping(value = "/records/{page}")
	public String getAllRecords(@Param("page") Integer page,Model model) {


		try{
			List<BookRecord> records = this.bookRecordService.getAllBookRecord();
			records.forEach(e-> System.out.println(e.toString()));
			model.addAttribute("records", records);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException Exception occurred in getAllRecords of Admin Book Controller");
			System.out.println("Actual message = " + e.getMessage());
		}
		catch(NullPointerException e)
		{
			System.out.println("SOme of the argument might be wrong ");
		}

		return "admin/getAllRecords";
	}

	@RequestMapping(value = "/records/{requestId}", method = RequestMethod.PUT, consumes = "*/*")
	public ResponseEntity<String> changeBookStatus(@RequestBody BookRecord bookRecord,
			@org.springframework.web.bind.annotation.PathVariable("requestId") int requestId, Principal principal) {

		try {
			bookRecord.setAdminId(principal == null ? "shivamadmin" : principal.getName());
			BookRecord oldBookRecord = this.bookRecordService.findBookRecordById(requestId);
			this.bookRecordService.updateBookRecordById(bookRecord, requestId);
		} catch (Exception e) {
			System.out.println("in update Book Record status  = " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return new ResponseEntity<>("Book status is changed" + bookRecord.getStatus() + " Successfully ",
				HttpStatus.OK);
	}
}
