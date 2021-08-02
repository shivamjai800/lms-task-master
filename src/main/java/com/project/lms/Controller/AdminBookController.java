package com.project.lms.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.project.lms.Entities.*;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.project.lms.Service.Implementation.BookRecordServiceImpl;
import com.project.lms.Service.Implementation.BookServiceImplementation;

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
	@GetMapping(value = "/records/{pageNo}")
	public String getAllRecords(@PathVariable("pageNo") int pageNo, Model model, Status status) {

		status.setReturned(true);
		status.setCancelled(true);
		status.setApproved(true);
		status.setRequested(true);
		try{
			Page<BookRecord> records = this.bookRecordService.getAllBookRecord(pageNo,status);

//			records.forEach(e-> System.out.println(e.toString()));
			model.addAttribute("records", records);
//			model.addAttribute("records",null);
			model.addAttribute("currentPage",pageNo);
			model.addAttribute("totalPages", records.getTotalPages());
			model.addAttribute(status);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException Exception occurred in getAllRecords of Admin Book Controller");
			System.out.println("Actual message = " + e.getMessage());
		}
		catch(NullPointerException e)
		{
			System.out.println("SOme of the argument might be wrong ");
			System.out.println(e.getMessage());
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

	@PostMapping(value = "/records/{pageNo}", consumes = "*/*")
	public String applyFilterOnBookRecords(@PathVariable("pageNo") int pageNo, Model model, Status status) {
		try{
			Page<BookRecord> records = this.bookRecordService.getAllBookRecord(pageNo,status);

			records.forEach(e-> System.out.println(e.toString()));
			model.addAttribute("records", records);
			model.addAttribute("currentPage",pageNo);
			model.addAttribute("totalPages", records.getTotalPages());
			model.addAttribute(status);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException Exception occurred in getAllRecords of Admin Book Controller");
			System.out.println("Actual message = " + e.getMessage());
		}
		catch(NullPointerException e)
		{
			System.out.println("SOme of the argument might be wrong ");
			System.out.println(e.getMessage());
		}

		return "admin/getAllRecords";
	}


	// <------ statistics -->

	@GetMapping("/statistics/books")
	public String getBookStatistics(Model model)
	{
		try
		{
			List<Pair<Book,Long>> statistics = this.bookRecordService.topBookRecords(10);
			statistics.forEach(e -> {
//				System.out.println("Here = ");
				System.out.println(e.getKey().toString()+" "+e.getValue().toString());

			});
			System.out.println("size = "+statistics.size());
			model.addAttribute("statistics", statistics);
		}
		catch (NullPointerException e)
		{
			System.out.println("Null Pointer Exception from getBooksStatistics from admin COntroller");
			System.out.println(e.getMessage());
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("Illegal Argument Exception from get getBooksStatistics from admin COntroller ");
			System.out.println(e.getMessage());
		}
		return "admin/bookStatistics";
	}

	@GetMapping("/statistics/user")
	public String getUserStatistics(Model model)
	{
		try
		{
			List<Pair<User,Long>> statistics = this.bookRecordService.topUserRecords(10);
//			statistics.forEach(e -> {
////				System.out.println("Here = ");
//				System.out.println(e.getKey().toString()+" "+e.getValue().toString());
//
//			});
//			System.out.println("size = "+statistics.size());
			model.addAttribute("statistics", statistics);
		}
		catch (NullPointerException e)
		{
			System.out.println("Null Pointer Exception from getBooksStatistics from admin COntroller");
			System.out.println(e.getMessage());
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("Illegal Argument Exception from get getBooksStatistics from admin COntroller ");
			System.out.println(e.getMessage());
		}
		return "admin/userStatistics";
	}
}
