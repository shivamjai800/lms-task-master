package com.project.lms.Controller;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.lms.Entities.Status;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/user")
public class UserBookController {

	@Autowired
	BookServiceImplementation bookService;

	@Autowired
	UserServiceImplementation userService;

	@Autowired
	BookRecordServiceImpl bookRecordService;

	@PostMapping("/books/search")
	public String search(@RequestParam("keyword") String keyword, Model model, Principal principal) {
		List<Book> books = new ArrayList<Book>();
		List<Integer> requestBookIds = new ArrayList<Integer>();
		List<Integer> approveBookIds = new ArrayList<Integer>();
		try {
			books = this.bookService.listByKeyword(keyword);
			books.forEach(e-> System.out.println(e));
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
	@RequestMapping(value = "/books/records/{bookId}", method = RequestMethod.POST, consumes = {"application/json"})
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

	@DeleteMapping("/books/records/{bookId}")
	public ResponseEntity<String> delete(@org.springframework.web.bind.annotation.PathVariable("bookId") int bookId,
			Principal principal) {

		try {
//			this.userService.removeBookRecordRequest(principal.getName(), bookId);
//			this.bookService.removeBookRecordRequest(bookId, principal.getName());
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
	
	@GetMapping(value="/records/{pageNo}")
	public String getAllRecords(@PathVariable("pageNo") int pageNo,Model model, Principal principal)
	{
		try
		{
//			User u = this.userService.getTheUser(principal.getName());
//			Map<String,Object> m = new Map<String, Object>() {
//			};
			Page<BookRecord> bookRecords = this.bookRecordService.findBookRecordByUserUsername(principal.getName(), pageNo);
			bookRecords.forEach(e -> System.out.println(e.toString()));
			model.addAttribute("records" ,bookRecords);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", bookRecords.getTotalPages());

			Status status = new Status();
			status.setApproved(true); status.setCancelled(true);
			status.setRequested(true); status.setReturned(true);
			model.addAttribute(status);
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
	@PutMapping(value = "/records/{recordId}",consumes = {"application/json"})
	public ResponseEntity<String> returnBook(@PathVariable("recordId") int recordId)
	{
		try
		{
			this.bookRecordService.approveBookRecordById(recordId);
		}
		catch (NullPointerException | IllegalArgumentException e)
		{
			System.out.println("NullPointerException or IllegalException occurred in getAllRecord of User Book Controller");
			System.out.println("May be username is null");
			System.out.println("Actual message = " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<>("SuccessFully Returned the book", HttpStatus.ACCEPTED);

	}
	@PostMapping(value="/records/{pageNo}",consumes = "*/*")
	public String applyFilterOnBookRecords(@PathVariable("pageNo") int pageNo,Model model, Principal principal, Status status)
	{
		try
		{
			System.out.println("Status function = "+status.toString());
			Page<BookRecord> bookRecords = this.bookRecordService.findBookRecordByUserUsername(principal.getName(), pageNo, status);
//			bookRecords.forEach(e -> System.out.println(e.toString()));
			model.addAttribute("records" ,bookRecords);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", bookRecords.getTotalPages());
			System.out.println(status.toString());
			model.addAttribute(status);
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
