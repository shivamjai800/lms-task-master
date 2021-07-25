package com.project.lms.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin/book")
public class AdminBookController {

	@Autowired
	BookServiceImplementation bookService;

	@Autowired
	BookRecordServiceImpl bookRecordService;

	@GetMapping("/addBook")
	public String addBookView(Model model) {
		model.addAttribute("book", new Book());
		return "admin/addBookView";
	}

	@PostMapping("")
	public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model) {
		System.out.println(book);
		if (result.hasErrors()) {
			model.addAttribute("book", book);
			return "admin/addBookView";
		}
		try {
			book = bookService.addBook(book);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "admin/dashboard";
	}

	@PostMapping("/search")
	public String search(@RequestParam("keyword") String keyword, Model model) {

		List<Book> books = new ArrayList<Book>();
		books = this.bookService.listByKeyword(keyword);
		model.addAttribute("books", books);
		return "admin/allBooks";
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@org.springframework.web.bind.annotation.PathVariable("id") int id) {
		try {

			this.bookService.deleteBookById(id);
		} catch (Exception e) {
			System.out.println("in delete request = " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "*/*")
	public ResponseEntity<Void> updateUser(@RequestBody Book book,
			@org.springframework.web.bind.annotation.PathVariable("id") int id) {

		try {
			this.bookService.updateBookById(book, id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

//  Approving the request related to the book.
	@GetMapping(value = "/getAllRecords")
	public String getAllRecords(Model model) {
		List<BookRecord> records = this.bookRecordService.getAllBookRecord();
		List<String> columnName = new ArrayList<String>();
		Field[] fields = BookRecord.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
			columnName.add(fields[i].getName());
		records.forEach(e -> {
			System.out.println(e.toString());
		});
		model.addAttribute("columnName", columnName);
		model.addAttribute("records", records);
		return "admin/getAllRecords";
	}

	@RequestMapping(value = "/changeBookStatus/{requestId}", method = RequestMethod.PUT, consumes = "*/*")
	public ResponseEntity<String> changeBookStatus(@RequestBody BookRecord bookRecord,
			@org.springframework.web.bind.annotation.PathVariable("requestId") int requestId, Principal principal) {

		try {
			bookRecord.setAdminId(principal == null ? "shivamadmin" : principal.getName());

			if (bookRecord.getStatus().equals("APPROVED")) {
				BookRecord oldBookRecord = this.bookRecordService.findBookRecordById(requestId);
				UnitBook unitBook = this.bookService.getBookById(oldBookRecord.getBookId()).getUnitBooks()
						.parallelStream().filter(e -> !e.isAssigned()).findFirst().get();
				
				bookRecord.setReturnDateTime(LocalDateTime.now().plusDays(7));
				bookRecord.setUnitBookReceived(unitBook.getId());
			}
			
			this.bookRecordService.updateBookRecordById(bookRecord, requestId);
		} catch (Exception e) {
			System.out.println("in update Book Record status  = " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return new ResponseEntity<>("Book status is changed" + bookRecord.getStatus() + " Successfully ",
				HttpStatus.OK);
	}
}
