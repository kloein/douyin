package com.learn.douyin.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.douyin.common.exception.DouyinException;
import com.learn.douyin.common.exception.ResultCodeEnum;
import com.learn.douyin.common.utils.MD5;
import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.user.mapper.UserMapper;
import com.learn.douyin.user.service.UserService;
import com.learn.model.response.LoginResponse;
import com.learn.model.response.RegisterResponse;
import com.learn.model.response.UserMsgResponse;
import com.learn.model.user.User;
import com.learn.model.user.UserMsg;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
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
        //插入
        baseMapper.insert(user);
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
        //4、TODO 查询关注信息

        //5、构造返回值
        UserMsg userMsg=new UserMsg();
        userMsg.setId(userId);
        userMsg.setUsername(user.getUsername());
        userMsg.setFollowCount(0L);
        userMsg.setFollowerCount(0L);
        userMsg.setFollow(false);
        return UserMsgResponse.ok(userMsg);
    }
}
