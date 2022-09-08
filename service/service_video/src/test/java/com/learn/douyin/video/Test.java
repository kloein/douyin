package com.learn.douyin.video;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.douyin.video.mapper.VideoMapper;
import com.learn.douyin.video.service.VideoService;
import com.learn.model.pojo.Video;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    @Autowired
    VideoService videoService;
    @org.junit.Test
    public void testSelect() {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", 1000);
        List<Video> list = videoService.list(wrapper);
        System.out.println(list);
    }
}
