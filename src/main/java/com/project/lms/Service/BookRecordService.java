package com.project.lms.Service;

import java.util.List;

import com.project.lms.Entities.BookRecord;

public interface BookRecordService {
	
	public BookRecord createBookRecord(BookRecord bookRecord);
	public void deleteBookRecordById(int requestId);
	public BookRecord findBookRecordById(int requestId);
	public List<BookRecord> getAllBookRecord();
	
	public void updateBookRecordById(BookRecord bNew, int requestId);
}
