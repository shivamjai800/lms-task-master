package com.project.lms.Repository;


import com.project.lms.Entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;
    private List<User> list = new ArrayList<User>();

    public void before()
    {
        for(int i=0;i<3;i++) {
            user = new User();
            user.setName("user_user"+i);
            user.setUsername("user_user"+i);
            user.setPassword("123456");
            user.setRoles("ROLE_USER");
            user.setActive(true);
            this.userRepository.save(user);
            list.add(user);
        }
        for(int i=0;i<3;i++) {
            user = new User();
            user.setName("user_admin"+i);
            user.setUsername("user_admin"+i);
            user.setPassword("123456");
            user.setRoles("ROLE_ADMIN");
            user.setActive(true);
            this.userRepository.save(user);
            list.add(user);
        }
    }
    public void after()
    {
        list.clear();
    }
    @Test
    @Rollback(value = true)
    public void saveUser()
    {
        before();
        userRepository.save(user);
        User user1 = userRepository.findByUsername(user.getUsername());
//        System.out.println(user1.toString()+" "+user.toString());
        assertTrue(user1.equals(user));
        after();
    }



    @Test
    @Rollback
    public void findAll()
    {
        before();
        List<User> l = this.userRepository.findAll();
        l.forEach(e -> {
            assertTrue(list.contains(e));
        });
        after();
    }
    @Test
    @Rollback
    public void findByUsername()
    {
        before();
        user = this.userRepository.findByUsername("user_user1");
        assertTrue(user.getUsername().equals("user_user1"));
        assertTrue(user.getRoles().equals("ROLE_USER"));
        after();
    }
    @Test
    @Rollback
    public void deleteByUsername()
    {
        before();
        this.userRepository.deleteByUsername("user_user1");
        user = this.userRepository.findByUsername("user_user1");
        assertTrue(user==null);

    }

    @Test
    @Rollback
    public void search()
    {
        before();
        List<User> l = this.userRepository.search("admin");
        assertEquals(3,l.size());
        l = this.userRepository.search("user");
        assertEquals(6,l.size());
    }


}

