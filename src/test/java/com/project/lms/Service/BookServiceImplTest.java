package com.project.lms.Service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.project.lms.Entities.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.project.lms.Service.Implementation.*;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.lms.Repository.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceImplTest{

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookServiceImplementation bookService;

    private Book book = new Book(1);

    @Before
    public void before()
    {
        book.setTitle("Alchemist");
        book.setAuthor("Paulo coelho");
        book.setQuantity(1);
    }

    @Test
    public void getBookById()
    {
        Mockito.when(this.bookRepository.findBookById(1)).thenReturn(book);
//        assertEquals(this.bookService);
    }

    @Test
    public void getAllBooks()
    {
        List<String> names = new ArrayList<>(Arrays.asList("book1", "book2","book3", "book4"));
        List<Book> list = new ArrayList<>();
        names.forEach(n -> {
            Book b = new Book();
            b.setTitle(n);
            list.add(b);
        });
        Mockito.when(bookRepository.findAll()).thenReturn((List<Book>)list);
        assertThat(this.bookService.getAllBooks()).isEqualTo(list);
    }

    @Test
    public void addBook()
    {
        Mockito.when(bookRepository.save(book)).thenReturn(book);
//        Book book1  = this.bookService.addBook(book);
//        assertTrue(book1.equals(book));

    }
//    public List<Book> getTheBookByAuthor(String author);

    @Test
    public void deleteBookById()
    {
//        Mockito.doThrow(new IllegalArgumentException("Null object deleted")).when(bookRepository).deleteBookById(null);
        Mockito.doNothing().when(bookRepository).deleteBookById(1);
        bookService.deleteBookById(1);
        Mockito.verify(bookRepository,Mockito.times(1)).deleteBookById(1);

    }


    @Test
    public void updateBookById()
    {
        Mockito.when(bookRepository.findBookById(1)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Book book1 = new Book();
        book1.setId(1); book1.setAuthor("Justice"); book1.setQuantity(25); book1.setTitle("jump");
        this.bookService.updateBookById(book1,1);
        assertTrue(book.equals(book1));
    }

    @Test
    public void listByKeyword()
    {
        List<String> names = new ArrayList<>(Arrays.asList("book1", "book2","book3", "book4"));
        List<Book> list = new ArrayList<>();
        names.forEach(n -> {
            Book b = new Book();
            b.setTitle(n);
            list.add(b);
        });

        Mockito.when(bookRepository.findAll()).thenReturn(list);
        assertThat(bookService.listByKeyword(null)).isEqualTo(list);
    }

    @Test
    public void removeBookRecordRequest()
    {
        BookRecord bookRecord = new BookRecord(); bookRecord.setBookId(1); bookRecord.setStatus("REQUESTED"); bookRecord.setUserUsername("shivamuser");
        List<BookRecord> list = new ArrayList<BookRecord>(); list.add(bookRecord);
        book.setRequest(list);

        Mockito.when(this.bookRepository.findBookById(1)).thenReturn(book);

        this.bookService.removeBookRecordRequest(1,"shivamuser");
        assertTrue(book.getRequest().isEmpty());
    }


}
