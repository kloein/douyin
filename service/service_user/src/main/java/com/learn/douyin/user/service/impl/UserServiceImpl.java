package com.learn.douyin.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.douyin.follow.client.FollowFeignClient;
import com.learn.douyin.common.exception.DouyinException;
import com.learn.douyin.common.exception.ResultCodeEnum;
import com.learn.douyin.common.utils.MD5;
import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.user.mapper.UserMapper;
import com.learn.douyin.user.service.UserService;
import com.learn.model.response.LoginResponse;
import com.learn.model.response.RegisterResponse;
import com.learn.model.response.UserMsgResponse;
import com.learn.model.pojo.User;
import com.learn.model.user.UserMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private FollowFeignClient followFeignClient;

    @Override
    public RegisterResponse registerUser(String username, String password) {
        //检验用户名与密码合法性
        if (StringUtils.isEmpty(username) || username.length() > 32
                || StringUtils.isEmpty(password) || password.length() > 32||password.length()<=5) {
            throw new DouyinException(ResultCodeEnum.REGISTER_PARAM_ERROR);
        }
        //检验用户名是否已存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = baseMapper.selectOne(wrapper);
        if (user != null) {//用户名已存在
            throw new DouyinException(ResultCodeEnum.USERNAME_HAS_EXIST);
        }
        //用户名不存在
        user=new User();
        String passwordMD5= MD5.encrypt(password);
        user.setUsername(username);
        user.setPassword(passwordMD5);
        //插入,username加了唯一主键，防止并发注册相同名字
        try {
            baseMapper.insert(user);
        } catch (DuplicateKeyException e) {
            throw new DouyinException(ResultCodeEnum.USERNAME_HAS_EXIST);
        }
        //构造返回值
        user=baseMapper.selectOne(wrapper);//获取id
        Long userId=user.getId();
        String token= TokenUtil.createToken(userId, username);
        return RegisterResponse.ok(userId, token);
    }

    @Override
    public LoginResponse loginUser(String username, String password) {
        //检验用户名与密码合法性
        if (StringUtils.isEmpty(username) || username.length() > 32
                || StringUtils.isEmpty(password) || password.length() > 32||password.length()<=5) {
            throw new DouyinException(ResultCodeEnum.REGISTER_PARAM_ERROR);
        }
        //从数据库中获取用户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = baseMapper.selectOne(wrapper);
        //用户不存在
        if (user == null) {
            throw new DouyinException(ResultCodeEnum.USER_NOT_EXIST);
        }
        //密码错误
        if (!user.getPassword().equals(MD5.encrypt(password))) {
            throw new DouyinException(ResultCodeEnum.PASSWORD_ERROR);
        }
        long userId = user.getId();
        String token = TokenUtil.createToken(userId, username);
        return LoginResponse.ok(userId, token);
    }

    @Override
    public UserMsgResponse getUserMsg(Long userId, String token) {
        //1、检验参数
        if (userId == null || token == null) {
            throw new DouyinException(ResultCodeEnum.PARAM_MISSING);
        }
        //2、检验token
        if (!TokenUtil.verify(token)) {
            throw new DouyinException(ResultCodeEnum.TOKEN_ERROR);
        }
        //3、查询用户基础信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", userId);
        User user = baseMapper.selectOne(userQueryWrapper);
        if (user == null) {
            throw new DouyinException(ResultCodeEnum.USER_NOT_EXIST);
        }
        //4、返回
        Long thisUid=TokenUtil.getUserId(token);
        UserMsg userMsg=this.packageUserMsg(user,thisUid);
        return UserMsgResponse.ok(userMsg);
    }

    @Override
    public List<UserMsg> getUserMsgByIds(List<Long> userIds,String token) {
        //如果为空，sql语句会出错
        if (userIds == null || userIds.size() == 0) {
            return new ArrayList<>();
        }

        //List<User> users = baseMapper.selectBatchIds(userIds);这样如果含重复uid只会查询出一个 userIds.size()>=users.size()
        //使用多线程查询
        CountDownLatch countDownLatch = new CountDownLatch(userIds.size());
        User[] userArr=new User[userIds.size()];
        for (int i = 0; i < userIds.size(); i++) {
            final Long userId=userIds.get(i);
            final int index=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        User user = baseMapper.selectById(userId);
                        userArr[index]=user;
                    }finally {
                        countDownLatch.countDown();
                    }
                }
            }).run();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //转为List
        List<User> users=new ArrayList<>(userArr.length);
        for (int i = 0; i < userArr.length; i++) {
            users.add(userArr[i]);
        }
        List<UserMsg> userMsgs=new ArrayList<>(users.size());
        Long thisUid=TokenUtil.getUserId(token);
        final UserMsg[] userMsgsArr=new UserMsg[users.size()];
        CountDownLatch countDownLatchUserMsg = new CountDownLatch(users.size());
        //使用多线程查询用户信息
        for (int i=0;i<users.size();i++) {
            final int index=i;
            final User user=users.get(i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        UserMsg userMsg = packageUserMsg(user,thisUid);
                        userMsgsArr[index]=userMsg;
                    }finally {
                        countDownLatchUserMsg.countDown();
                    }
                }
            }).start();
        }
        try {
            countDownLatchUserMsg.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //数组转化为list
        for(int i=0;i<userMsgsArr.length;i++){
            userMsgs.add(userMsgsArr[i]);
        }
        return userMsgs;
    }

    public UserMsg packageUserMsg(User user,Long thisUid) {
        UserMsg userMsg = new UserMsg();
        userMsg.setId(user.getId());
        userMsg.setUsername(user.getUsername());
        Map<String, Object> map = followFeignClient.userFollowMsg(user.getId(), thisUid);
        Integer followCnt= (Integer) map.get("followCnt");
        Integer followerCnt= (Integer) map.get("followerCnt");
        Boolean isFollow= (Boolean) map.get("isFollow");
        userMsg.setFollowCount(followCnt);
        userMsg.setFollowerCount(followerCnt);
        userMsg.setFollow(isFollow);
        return userMsg;
    }
}
