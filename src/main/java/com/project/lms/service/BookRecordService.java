package com.project.lms.service;

import java.util.List;

import com.project.lms.entities.Book;
import com.project.lms.entities.BookRecord;
import com.project.lms.entities.Status;
import com.project.lms.entities.User;
import javafx.util.Pair;
import org.springframework.data.domain.Page;

public interface BookRecordService {
	
	public BookRecord createBookRecord(BookRecord bookRecord,String userUsername,int bookId);
	public void deleteBookRecordById(int requestId);
	public BookRecord findBookRecordById(int requestId);
	public Page<BookRecord> getAllBookRecord(int pageNo, Status status);
	
	public void updateBookRecordById(BookRecord bNew, int requestId);

	public void removeBookRecordByUsernameAndBookId(String userUsername,int bookId);

    public void approveBookRecordById(int recordId);
	
    public Page<BookRecord> findBookRecordByUserUsername(String username, int pageNo);

    public Page<BookRecord> findBookRecordByUserUsername(String username, int pageNo, Status status);

    public List<Pair<Book,Long>> topBookRecords(int size);

	public List<Pair<User,Long>> topUserRecords(int size);
}
