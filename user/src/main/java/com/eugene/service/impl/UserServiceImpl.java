package com.eugene.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.mapper.UserMapper;
import com.eugene.pojo.User;
import com.eugene.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/3/17 12:00
 */
@Service
@DS("master")
@Deprecated
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list(QueryWrapper<User> queryWrapper) {
        return userMapper.selectList(queryWrapper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveBatch(List<User> userList) {
        return userMapper.saveBatch(userList);
    }
}
