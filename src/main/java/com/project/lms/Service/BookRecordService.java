package com.project.lms.Service;

import java.util.List;
import java.util.Map;

import com.project.lms.Entities.Book;
import com.project.lms.Entities.BookRecord;
import com.project.lms.Entities.Status;
import com.project.lms.Entities.User;
import javafx.util.Pair;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

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
