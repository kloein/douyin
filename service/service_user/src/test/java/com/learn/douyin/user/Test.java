package com.learn.douyin.user;

import com.learn.douyin.user.mapper.UserMapper;
import com.learn.model.pojo.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
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

    @Autowired
    UserMapper userMapper;
    @org.junit.Test
    public void testDuplicatedKey() {
        User user=new User();
        user.setUsername("tom");
        user.setPassword("test");
        try {
            System.out.println(userMapper.insert(user));
        } catch (DuplicateKeyException e) {
            System.out.println("重复key");
        }
    }
}
