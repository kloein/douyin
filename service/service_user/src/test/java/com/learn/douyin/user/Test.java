package com.learn.douyin.user;

import com.learn.model.pojo.User;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    @org.junit.Test
    public void testGetId() {
        User user = new User();
        System.out.println(user.getId());
        User user1 = new User();
        System.out.println(user1.getId());
    }
}
