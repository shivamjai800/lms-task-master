package com.project.lms.Repository;

import com.project.lms.Entities.Book;
import com.project.lms.Entities.BookRecord;
import com.project.lms.Entities.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class BookRecordRepositoryTest {

    @Autowired
    private BookRecordRepository bookRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    private BookRecord bookRecord;
    private List<BookRecord> list = new ArrayList<BookRecord>();
    public void before()
    {
        Book book = new Book();
        book.setTitle("title1");
        book.setAuthor("author1");
        User user = new User();
        user.setUsername("userr");
        user.setPassword("123456");
        user.setName("user");
        this.bookRepository.save(book);
        this.userRepository.save(user);
        for(int i=0;i<3;i++)
        {
            bookRecord = new BookRecord();
            bookRecord.setBookId(book.getId());
            bookRecord.setStatus("REQUESTED");
            bookRecord.setUserUsername(user.getUsername());
            bookRecord.setBookId(book.getId());
            user.getRequest().add(bookRecord);
            book.getRequest().add(bookRecord);
            bookRecord = this.bookRecordRepository.save(bookRecord);
            list.add(bookRecord);
        }
    }
    public void after()
    {
        list.clear();
    }

    @Test
    @Rollback
    public void deleteBookRecordById()
    {
        before();
        int id = bookRecord.getId();
        this.bookRecordRepository.deleteBookRecordById(id);
        BookRecord bookRecord1 = this.bookRecordRepository.findBookRecordById(id);
        Assert.assertNull(bookRecord1);
        after();
    }

    @Test
    @Rollback
    public void findBookRecordById()
    {
        before();
        BookRecord bookRecord1 = this.bookRecordRepository.findBookRecordById(bookRecord.getId());
        Assert.assertEquals(bookRecord,bookRecord1);
        after();
    }

    @Test
    @Rollback
    public void findAll()
    {
        before();
        List<BookRecord> l = this.bookRecordRepository.findAll();
        Assert.assertEquals(list.size(),l.size());
        after();
    }

}
