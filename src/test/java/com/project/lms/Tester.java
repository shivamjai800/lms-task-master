package com.project.lms;


import com.project.lms.Exception.BookApproveException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Tester {

    @Test
    public void bookExceptionTest()
    {
        try
        {
            throw new BookApproveException("Cannot Approve the Book","BookApprove");
        }
        catch (BookApproveException e)
        {
            System.out.println(e.getClass()+" "+e.getMessage()+" "+e.getName());
        }

    }
}
