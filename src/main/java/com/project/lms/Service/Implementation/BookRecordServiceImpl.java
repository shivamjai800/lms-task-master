package com.project.lms.Service.Implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lms.Entities.BookRecord;
import com.project.lms.Repository.BookRecordRepository;
import com.project.lms.Service.BookRecordService;

@Service
public class BookRecordServiceImpl implements BookRecordService {

	@Autowired
	BookRecordRepository bookRecordRepository;

	@Transactional
	public void deleteBookRecordById(int requestId) {
		this.bookRecordRepository.deleteBookRecordById(requestId);
	}

	@Override
	public BookRecord createBookRecord(BookRecord bookRecord, String userUsername,int bookId) {
		bookRecord.setStatus("REQUESTED");
		bookRecord.setBookId(bookId);
		bookRecord.setUserUsername(userUsername);
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
		bOld.setAdminId(bNew.getAdminId());
		bOld.setUnitBookReceived(bNew.getUnitBookReceived());
		bOld.setRemarks(bNew.getRemarks());
		bOld.setReturnDateTime(bNew.getReturnDateTime());
		
		this.bookRecordRepository.save(bOld);
		
	}
}
