package com.project.lms;


import com.project.lms.Service.Implementation.BookServiceImplementation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

import com.project.lms.Entities.*;
import com.project.lms.Repository.*;
import com.project.lms.Service.Implementation.BookRecordServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRecordServiceImplTest {

    @MockBean
    BookRecordRepository bookRecordRepository;

    @MockBean
    BookServiceImplementation bookServiceImplementation;


    @Autowired
    BookRecordServiceImpl bookRecordService;


    BookRecord bookRecord = new BookRecord();

    @Before
    public void before()
    {
        bookRecord.setId(1);
        bookRecord.setBookId(1);
        bookRecord.setUserUsername("shivamuser");
        bookRecord.setAdminId("shivamadmin");
        bookRecord.setStartDateTime(LocalDateTime.now());
        bookRecord.setRemarks("");
    }
    @Test
    public void createBookRecord(){
        Mockito.when(bookRecordRepository.save(bookRecord)).thenReturn(bookRecord);
        BookRecord bookRecord1 = this.bookRecordService.createBookRecord(bookRecord,"shivamuser",1);
        assertThat(bookRecord1).isEqualTo(bookRecord);
        assertTrue(bookRecord1.getStatus().equals("REQUESTED"));
    }

    @Test
    public void deleteBookRecordById(){
        Mockito.doNothing().when(bookRecordRepository).deleteBookRecordById(1);
        bookRecordService.deleteBookRecordById(1);
        Mockito.verify(bookRecordRepository,Mockito.times(1)).deleteBookRecordById(1);
    }

    @Test
    public void findBookRecordById(){
        Mockito.when(bookRecordRepository.findBookRecordById(1)).thenReturn(bookRecord);
        assertThat(bookRecordService.findBookRecordById(1)).isEqualTo(bookRecord);
    }

    @Test
    public void getAllBookRecord(){
        List<String> names = new ArrayList<>(Arrays.asList("book1","book2","book3"));
        List<BookRecord> list = new ArrayList<BookRecord>();
        names.forEach(e -> {
            BookRecord book1 = new BookRecord();
            book1.setRemarks(e);
            list.add(book1);
        });
        Mockito.when(bookRecordRepository.findAll()).thenReturn(list);
        assertThat(this.bookRecordService.getAllBookRecord()).isEqualTo(list);
    }

    @Test
    public void updateBookRecordById(){

        BookRecord bookRecord1 = new BookRecord(); bookRecord1.setBookId(1);
        Book book = new Book(); book.setId(1);
        UnitBook unitBook = new UnitBook();unitBook.setId("test unit Book"); unitBook.setAssigned(false);
        Set<UnitBook> s = new HashSet<UnitBook>(); s.add(unitBook);
        book.setUnitBooks(s);
        Mockito.when(this.bookRecordRepository.findBookRecordById(1)).thenReturn(bookRecord1);
        Mockito.when(this.bookRecordRepository.save(bookRecord1)).thenReturn(bookRecord1);
        Mockito.when(this.bookServiceImplementation.getBookById(1)).thenReturn(book);

        bookRecord.setStatus("APPROVED");
        this.bookRecordService.updateBookRecordById(bookRecord,1);
//        assertTrue(bookRecord1.getAdminId().equals(bookRecord.getAdminId()));
    }

    @Test
    public void removeBookRecordByUsernameAndBookId()
    {
        List<BookRecord> list = new ArrayList<BookRecord>();
        bookRecord.setStatus("REQUESTED");
        list.add(bookRecord);

        Mockito.when(this.bookRecordRepository.findAll()).thenReturn(list);
        Mockito.doNothing().when(this.bookRecordRepository).deleteBookRecordById(1);

        this.bookRecordService.removeBookRecordByUsernameAndBookId("shivamuser",1);
        Mockito.verify(this.bookRecordRepository,Mockito.times(1)).deleteBookRecordById(1);



    }
}
