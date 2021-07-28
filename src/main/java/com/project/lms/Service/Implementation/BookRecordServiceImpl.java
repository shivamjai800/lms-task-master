package com.project.lms.Service.Implementation;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.project.lms.Entities.Book;
import com.project.lms.Entities.UnitBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lms.Entities.BookRecord;
import com.project.lms.Repository.BookRecordRepository;
import com.project.lms.Service.BookRecordService;

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
	public List<BookRecord> getAllBookRecord() {
		return this.bookRecordRepository.findAll();
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


}
