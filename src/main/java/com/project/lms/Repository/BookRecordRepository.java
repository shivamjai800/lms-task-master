package com.project.lms.Repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.project.lms.Entities.Book;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.project.lms.Entities.BookRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRecordRepository extends PagingAndSortingRepository<BookRecord, Integer>, JpaSpecificationExecutor<BookRecord> {
	
	 @Transactional
	  @Modifying
	@Query(value = "DELETE FROM BookRecord e WHERE e.id = :id")  
	public void deleteBookRecordById(@Param("id") int id);
	 
	 @Query(value = "from BookRecord e where e.id = :id")
	public BookRecord findBookRecordById(@Param("id") int id);
	 
	public List<BookRecord> findAll();

	public Page<BookRecord> findAll(Specification<BookRecord> spec, Pageable page);

	@Query(value = "SELECT bR from BookRecord bR where  bR.userUsername = :userUsername")
	public Page<BookRecord> findBookRecordByUserUsername(String userUsername, Pageable perPageable);

	@Query(value = "select b.id,b.author, b.quantity,b.title, COUNT(bR.bookId) from Book as b inner join BookRecord as bR on b.id=bR.bookId where bR.startDateTime>=:required_date_time group by b")
	public List<Object[]> topRecords(LocalDateTime required_date_time,Pageable perPageable );

	@Query(value = "select u, COUNT(bR.userUsername) from User as u inner join BookRecord as bR on u.username=bR.userUsername where bR.startDateTime>=:required_date_time group by u")
	public List<Object[]> topUsers(LocalDateTime required_date_time,Pageable perPageable );


}
