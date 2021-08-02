package com.project.lms.Repository;

import com.project.lms.Entities.Book;
import com.project.lms.Entities.BookRecord;
import com.project.lms.Entities.User;
import javafx.util.Pair;
import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
            bookRecord.setStartDateTime(LocalDateTime.now());
//            user.getRequest().add(bookRecord);
//            book.getRequest().add(bookRecord);
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

    @Test
    @Rollback
    public void test()
    {
        before();
        list.forEach(e ->{
            System.out.println(e.toString());
        });
        LocalDateTime temp = LocalDateTime.now().minusDays(30);
        Pageable pageable = PageRequest.of(0,3);
        List<Object[]> l = this.bookRecordRepository.topUsers(temp,pageable);
        l.forEach(e->{
            Arrays.asList(e).forEach(f->{
                System.out.println(f.toString());
            });
            System.out.println();
        });
//        System.out.println("Size = "+l.size());
//        List<Pair<Book,Long>> pairs = new ArrayList<>();
//
//        l.forEach(e -> {
//
//            Book book = new Book((Integer)e[0]);
//            book.setAuthor((String) e[1]);
//            book.setQuantity((Integer)e[2]);
//            book.setTitle((String)e[3]);
////            System.out.println(book.toString());
//            Pair<Book,Long> p = new Pair<>(book,(Long) e[4]);
//            System.out.println(p.toString());
//            pairs.add(p);
//
//            System.out.println();
////            System.out.println("here = " +e.toString());
//        });
    }

}
