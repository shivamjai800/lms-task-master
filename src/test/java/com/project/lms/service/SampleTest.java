package com.project.lms.service;

import com.project.lms.entities.User;
import com.project.lms.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest
public class SampleTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(value = true)
    public void test()
    {
        User user = new User();
        user.setName("test");
        user.setUsername("test");
        user.setPassword("123456");
        user.setRoles("ROLE_ADMIN");
        user.setActive(true);
        userRepository.save(user);

        User user1 = userRepository.findByUsername("test");
//        assertTrue(user1.equals(user));
    }
}
