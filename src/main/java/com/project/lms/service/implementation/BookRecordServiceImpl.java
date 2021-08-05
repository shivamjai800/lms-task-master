package com.project.lms.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.project.lms.entities.*;
import com.project.lms.exception.BookApproveException;
import com.project.lms.repository.BookRecordSpecs;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.lms.repository.BookRecordRepository;
import com.project.lms.service.BookRecordService;

@Service
public class BookRecordServiceImpl implements BookRecordService {

	@Autowired
	BookRecordRepository bookRecordRepository;

	@Autowired
	private BookServiceImplementation bookService;

	@Autowired
	private UnitBookServiceImplementation unitBookService;

	@Transactional
	public void deleteBookRecordById(int requestId) {
		this.bookRecordRepository.deleteBookRecordById(requestId);
	}

	@Override
	public BookRecord createBookRecord(BookRecord bookRecord, String userUsername,int bookId) {
		bookRecord.setStatus("REQUESTED");
		bookRecord.setBookId(bookId);
		bookRecord.setUserUsername(userUsername);
		bookRecord.setStartDateTime(LocalDateTime.now());
		return this.bookRecordRepository.save(bookRecord);
	}

	@Override
	public BookRecord findBookRecordById(int requestId) {
		return this.bookRecordRepository.findBookRecordById(requestId);
	}

	@Override
	public Page<BookRecord> getAllBookRecord(int pageNo,Status status) {
		Pageable pageable = PageRequest.of(pageNo, 2, Sort.by("startDateTime").descending());
		Page<BookRecord> bookRecords =this.bookRecordRepository.findAll(BookRecordSpecs.filterStatus(status),pageable);
		return bookRecords;
	}

	@Override
	public void updateBookRecordById(BookRecord bNew, int requestId) throws BookApproveException {
		BookRecord bOld = this.bookRecordRepository.findBookRecordById(requestId);
		bOld.setStatus(bNew.getStatus());
		if(bNew.getStatus().equals("APPROVED")) {
			Optional<UnitBook> optionalUnitBook = this.bookService.getBookById(bOld.getBookId()).getUnitBooks()
					.parallelStream().filter(e -> !e.isAssigned()).findFirst();

			if(!optionalUnitBook.isPresent())
				throw new BookApproveException("Cannot Approve Book Exception as Book Not available","Cannot Approve");
			UnitBook unitBook = optionalUnitBook.get();
			bOld.setUnitBookReceived(unitBook.getId());
			unitBook.setAssigned(true);
		}
		bOld.setAdminId(bNew.getAdminId());
		bOld.setRemarks(bNew.getRemarks());
		bOld.setReturnDateTime(LocalDateTime.now().plusDays(7));
		
		this.bookRecordRepository.save(bOld);
		
	}

	@Override
	public void removeBookRecordByUsernameAndBookId(String userUsername, int bookId) {
		List<BookRecord> bookRecords = this.bookRecordRepository.findAll();
		BookRecord br = bookRecords.stream().filter(e->(e.getUserUsername().equals(userUsername) && e.getBookId()==bookId && e.getStatus().equals("REQUESTED"))).findAny().get();
		System.out.println("Delete mapping book Record = "+br.toString());
		this.bookRecordRepository.deleteBookRecordById(br.getId());
		return;
	}

	@Override
	public void approveBookRecordById(int recordId) {
		BookRecord bookRecord = this.bookRecordRepository.findBookRecordById(recordId);
		bookRecord.setStatus("RETURNED");
		UnitBook unitBook = this.unitBookService.getUnitBookById(bookRecord.getUnitBookReceived());
		unitBook.setAssigned(false);
		this.unitBookService.updateUnitBookBy(unitBook,bookRecord.getUnitBookReceived());
		return;
	}


	@Override
	public Page<BookRecord> findBookRecordByUserUsername(String username, int pageNo ) {

		Pageable pageable = PageRequest.of(pageNo, 5);
		Page<BookRecord> bookRecords = this.bookRecordRepository.findBookRecordByUserUsername(username,pageable);
		return bookRecords;
	}
	@Override
	public Page<BookRecord> findBookRecordByUserUsername(String username, int pageNo, Status status ) {


		Pageable pageable = PageRequest.of(pageNo, 1);


		Page<BookRecord> bookRecords = this.bookRecordRepository.findAll(BookRecordSpecs.filterByUsernameAndStatus(username,status),pageable);
		System.out.println("Status = "+status.toString());
//		bookRecords.forEach(e-> System.out.println("custom query"+e.toString()));
//		Page<BookRecord> bookRecords = this.bookRecordRepository.findBookRecordByUserUsername(username,pageable);
		return bookRecords;


	}

	@Override
	public List<Pair<Book,Long>> topBookRecords(int size) {
		Pageable pageable = PageRequest.of(0,size);
		LocalDateTime required_date_time = LocalDateTime.now().minusDays(30);
		List<Object[]> list = this.bookRecordRepository.topRecords(required_date_time, pageable);
		List<Pair<Book,Long>> pairs = new ArrayList<>();
		list.forEach(e->{
			Book book = new Book((Integer)e[0]);
			book.setAuthor((String) e[1]);
			book.setQuantity((Integer)e[2]);
			book.setTitle((String)e[3]);
			Pair<Book,Long> p = new Pair<>(book,(Long) e[4]);
//			System.out.println(p.toString());
			pairs.add(p);
		});
		return pairs;
//		return null;

	}

	@Override
	public List<Pair<User, Long>> topUserRecords(int size) {
		Pageable pageable = PageRequest.of(0,size);
		LocalDateTime required_date_time = LocalDateTime.now().minusDays(30);
		List<Object[]> list = this.bookRecordRepository.topUsers(required_date_time, pageable);
		List<Pair<User,Long>> pairs = new ArrayList<>();
		list.forEach(e->{
			User user = (User) e[0];
			Pair<User,Long> p = new Pair<>(user,(Long) e[1]);
			System.out.println(user.toString()+" "+p.getValue());
			pairs.add(p);
		});
		return pairs;
	}


}
