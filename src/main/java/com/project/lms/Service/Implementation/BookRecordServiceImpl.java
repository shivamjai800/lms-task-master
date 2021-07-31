package com.project.lms.Service.Implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.project.lms.Entities.Book;
import com.project.lms.Entities.Status;
import com.project.lms.Entities.UnitBook;
import com.project.lms.Repository.BookRecordSpecs;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.lms.Entities.BookRecord;
import com.project.lms.Repository.BookRecordRepository;
import com.project.lms.Service.BookRecordService;
import org.springframework.web.bind.annotation.RequestBody;

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
	public void updateBookRecordById(BookRecord bNew, int requestId) {
		BookRecord bOld = this.bookRecordRepository.findBookRecordById(requestId);
		bOld.setStatus(bNew.getStatus());
		if(bNew.getStatus().equals("APPROVED")) {
			UnitBook unitBook = this.bookService.getBookById(bOld.getBookId()).getUnitBooks()
					.parallelStream().filter(e -> !e.isAssigned()).findFirst().get();
			bOld.setUnitBookReceived(unitBook.getId());
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


}
