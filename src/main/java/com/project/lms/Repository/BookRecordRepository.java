package com.project.lms.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.lms.Entities.BookRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRecordRepository extends CrudRepository<BookRecord, Integer> {
	
	 @Transactional
	  @Modifying
	@Query(value = "DELETE FROM BookRecord e WHERE e.id = :id")  
	public void deleteBookRecordById(@Param("id") int id);
	 
	 @Query(value = "from BookRecord e where e.id = :id")
	public BookRecord findBookRecordById(@Param("id") int id);
	 
	public List<BookRecord> findAll();
	
}
