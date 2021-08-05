package com.project.lms.repository;

import com.project.lms.entities.Book;
import com.project.lms.entities.BookRecord;
import com.project.lms.entities.Status;
import com.project.lms.entities.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
            String status = i%2==0? "RETURNED": "APPROVED";
            bookRecord.setStatus(status);
            bookRecord.setUserUsername(user.getUsername());
            bookRecord.setBookId(book.getId());
            bookRecord.setStartDateTime(LocalDateTime.now().minusDays(2));
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
        Assert.assertEquals(list,l);
        after();
    }

    @Test
    @Rollback
    public void findAllSpecification()
    {
        before();
        Status status = new Status();
        status.setRequested(true);
        status.setApproved(false);
        status.setCancelled(false);
        status.setReturned(false);
        Pageable pageable = PageRequest.of(0,3);
        Page<BookRecord> l = this.bookRecordRepository.findAll(BookRecordSpecs.filterStatus(status),pageable);
        Assert.assertEquals(0,l.getTotalPages());
        Assert.assertEquals(0 ,l.getTotalElements());

        status.setApproved(true);
        l = this.bookRecordRepository.findAll(BookRecordSpecs.filterStatus(status),pageable);
        Assert.assertEquals(1,l.getTotalPages());
        Assert.assertEquals(1,l.getTotalElements());
        after();
    }




    @Test
    @Rollback
    public void findBookRecordByUserUsername()
    {
        before();
        Pageable pageable = PageRequest.of(0,2);
        Page<BookRecord> page = this.bookRecordRepository.findBookRecordByUserUsername("userr",pageable);
        Assert.assertEquals(2,page.getTotalPages());
        Assert.assertEquals(2,page.getNumberOfElements());
        Assert.assertEquals(3,page.getTotalElements());
        after();
    }

    @Test
    @Rollback
    public void topRecords()
    {
        before();
        LocalDateTime x = LocalDateTime.now().minusDays(3);
        Pageable pageable = PageRequest.of(0,3);
        List<Object[]> list1 = this.bookRecordRepository.topRecords(x,pageable);
        Assert.assertEquals(1, list1.size());
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
