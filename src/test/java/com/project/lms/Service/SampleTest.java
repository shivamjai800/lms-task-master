package com.project.lms.Service;

import com.project.lms.Entities.User;
import com.project.lms.Repository.UserRepository;
import com.project.lms.Service.Implementation.UserServiceImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
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
