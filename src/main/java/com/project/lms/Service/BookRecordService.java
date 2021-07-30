package com.project.lms.Service;

import java.util.List;
import java.util.Map;

import com.project.lms.Entities.BookRecord;
import com.project.lms.Entities.Status;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookRecordService {
	
	public BookRecord createBookRecord(BookRecord bookRecord,String userUsername,int bookId);
	public void deleteBookRecordById(int requestId);
	public BookRecord findBookRecordById(int requestId);
	public List<BookRecord> getAllBookRecord();
	
	public void updateBookRecordById(BookRecord bNew, int requestId);

	public void removeBookRecordByUsernameAndBookId(String userUsername,int bookId);

    public void approveBookRecordById(int recordId);
	
    public Page<BookRecord> findBookRecordByUserUsername(String username, int pageNo);

    public Page<BookRecord> findBookRecordByUserUsername(String username, int pageNo, Status status);
}
