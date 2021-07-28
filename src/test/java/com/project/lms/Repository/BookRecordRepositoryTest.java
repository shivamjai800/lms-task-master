package com.project.lms.Repository;

import com.project.lms.Entities.Book;
import com.project.lms.Entities.BookRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BookRecordRepositoryTest {

    @Autowired
    private BookRecordRepository bookRecordRepository;

    private BookRecord bookRecord;
    private List<BookRecord> list;
    public void before()
    {
        for(int i=0;i<3;i++)
        {
            bookRecord = new BookRecord();
            bookRecord.setStatus("ACTIVE");
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
        bookRecord = new BookRecord();
        this.bookRecordRepository.save(bookRecord);
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
