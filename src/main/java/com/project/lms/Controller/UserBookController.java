package com.project.lms.Controller;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.*;
import com.project.lms.Entities.Book;
import com.project.lms.Entities.BookRecord;
import com.project.lms.Entities.User;
import com.project.lms.Service.Implementation.BookRecordServiceImpl;
import com.project.lms.Service.Implementation.BookServiceImplementation;
import com.project.lms.Service.Implementation.UserServiceImplementation;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/user/book")
public class UserBookController {

	@Autowired
	BookServiceImplementation bookService;

	@Autowired
	UserServiceImplementation userService;

	@Autowired
	BookRecordServiceImpl bookRecordService;

	@PostMapping("/search")
	public String search(@RequestParam("keyword") String keyword, Model model, Principal principal) {
		List<Book> books = new ArrayList<Book>();
		List<Integer> requestBookIds = new ArrayList<Integer>();
		List<Integer> approveBookIds = new ArrayList<Integer>();
		try {
			books = this.bookService.listByKeyword(keyword);
			User u = this.userService.getTheUser(principal.getName());

			u.getRequest().stream().filter(e-> e.getStatus().equals("REQUESTED")).forEach(e-> requestBookIds.add(e.getBookId()));
			u.getRequest().stream().filter(e-> e.getStatus().equals("APPROVED")).forEach(e-> approveBookIds.add(e.getBookId()));
		}
		catch(NullPointerException e)
		{
			System.out.println("NullPointerException Exception occurred in search of User Book Controller");
			System.out.println("Either principal is null or  user u in null");
			System.out.println("Actual message = " + e.getMessage());
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException Exception occurred in search of User Book Controller");
			System.out.println("Either user is null");
			System.out.println("Actual message = " + e.getMessage());
		}
		model.addAttribute("books", books);
		model.addAttribute("requestBookIds", requestBookIds);
		model.addAttribute("approveBookIds", approveBookIds);
		return "user/allBooks";
	}

//	<----- BookRecordRequest  ------>
	@RequestMapping(value = "/request/{bookId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> request(@org.springframework.web.bind.annotation.PathVariable("bookId") int bookId,
			@RequestBody BookRecord bookRecord, Model model, Principal principal) {

		try {
			User u = this.userService.getTheUser(principal.getName());
			Book b = this.bookService.getBookById(bookId);
			this.bookRecordService.createBookRecord(bookRecord,principal.getName(),bookId);
			this.bookService.updateBookById(b, bookId);
			this.userService.updateUser(u, principal.getName());
		}
		catch(NullPointerException e)
		{
			System.out.println("NullPointerException Exception occurred in request of User Book Controller");
			System.out.println("Either principal is null or  bookId in null");
			System.out.println("Actual message = " + e.getMessage());
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("IllegalArgument Exception occurred in request of User Book Controller");
			System.out.println("Trying to save Null object");
			System.out.println("Actual message = " + e.getMessage());
		}

		return new ResponseEntity<>("Requested is created for the user", HttpStatus.OK);
	}

	@DeleteMapping("/request/{bookId}")
	public ResponseEntity<String> delete(@org.springframework.web.bind.annotation.PathVariable("bookId") int bookId,
			Principal principal) {

		try {
			this.userService.removeBookRecordRequest(principal.getName(), bookId);
			this.bookService.removeBookRecordRequest(bookId);
			this.bookRecordService.removeBookRecordByUsernameAndBookId(principal.getName(),bookId);
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception occurred in delete bookrecord of User Book Controller");
			System.out.println("either user is null or book is null");
			System.out.println("Actual message = " + e.getMessage());
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException occurred in getAllRecord of User Book Controller");
			System.out.println("Trying to get null pricipal object");
			System.out.println("Actual message = " + e.getMessage());
		}

		return new ResponseEntity<>("Book with Book Id = " + bookId + "  is deleted ", HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value="/getAllRecords")
	public String getAllRecords(Model model, Principal principal)
	{
		try
		{
			User u = this.userService.getTheUser(principal.getName());
			model.addAttribute("records" ,u.getRequest());
		}
		catch (NullPointerException e)
		{
			System.out.println("Null Pointer Exception occurred in getAllRecord of User Book Controller");
			System.out.println("Trying to get null pricipal object");
			System.out.println("Actual message = " + e.getMessage());
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException occurred in getAllRecord of User Book Controller");
			System.out.println("May be username is null");
			System.out.println("Actual message = " + e.getMessage());
		}

		return "user/getAllRecords";
	}



}
