package com.eugene.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.mapper.UserMapper;
import com.eugene.pojo.User;
import com.eugene.service.IUserShardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description 用户分表操作类
 * @Author eugene
 * @Data 2023/3/17 12:00
 */
@Service
@DS("shardingmaster")
public class UserShardingServiceImpl extends ServiceImpl<UserMapper, User> implements IUserShardingService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(User user) {
        return retBool(userMapper.insert(user));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveBatch(List<User> userList) {
        return userMapper.saveBatch(userList);
    }

}
