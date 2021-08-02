package com.project.lms.Repository;

import com.project.lms.Entities.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    private Book book;
    private List<Book> list = new ArrayList<>();

    public void before()
    {
        for(int i=1;i<=4;i++)
        {
            book = new Book();
            book.setTitle("Book"+i);
            book.setAuthor("Author"+i);
            book.setQuantity(3);

             book = this.bookRepository.save(book);
            list.add(book);
        }
    }
    public void after()
    {
        list.clear();
    }

    @Test
    @Rollback(value = true)
    public void findByKeyword()
    {
        before();
        List<Book> l = this.bookRepository.findByKeyword("Book");
        assertEquals(list, l);
//        l = this.bookRepository.findByKeyword("Author1");
//        assertEquals(1,l.size());
        l = this.bookRepository.findByKeyword("Author");
        assertEquals(4,l.size());
        after();
    }

    @Test
    @Rollback(value = true)
    public void deleteBookById()
    {
        before();
        this.bookRepository.deleteBookById(0);
        assertNull(this.bookRepository.findBookById(0));
        after();
    }
    @Test
    @Rollback
    public void findById()
    {
        before();
        Book book1 = new Book();
        book1.setAuthor("author99"); book1.setTitle("title99");
        this.bookRepository.save(book1);

        Book b = this.bookRepository.findBookById(book1.getId());

        assertNotNull(b);
        assertEquals("author99", b.getAuthor());
        after();
    }

    @Test
    @Rollback
    public void findAll()
    {
        before();
        List<Book> l = this.bookRepository.findAll();
        assertEquals(list,l);
        after();
    }

}
