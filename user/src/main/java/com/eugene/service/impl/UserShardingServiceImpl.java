package com.eugene.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.controller.request.RegisterUserRequest;
import com.eugene.mapper.UserMapper;
import com.eugene.mq.producer.RegisterUserProducerService;
import com.eugene.mq.producer.TxRegisterUserProducerService;
import com.eugene.pojo.User;
import com.eugene.service.IMqTransactionLogService;
import com.eugene.service.IUserShardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.eugene.pojo.User.converUser;

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
    @Autowired
    private RegisterUserProducerService registerUserProducerService;
    @Autowired
    private IMqTransactionLogService mqTransactionLogService;
    @Autowired
    private TxRegisterUserProducerService txRegisterUserProducerService;

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


    @Override
    public boolean register(RegisterUserRequest request) {
        User user = converUser(request);
        boolean saved = this.save(user);
        if (saved) {
            // 发送用户注册消息
            registerUserProducerService.syncSendRegisterMessage(user);
            return true;
        }
        return false;
        // 发送事务消息
//        txRegisterUserProducerService.sendHalfMessage(user);
//        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerTx(User user, String transactionId) {
        // 保存用户
        save(user);
        // 写入mq事务日志
        mqTransactionLogService.saveMQTransactionLog(transactionId, user);
        return true;
    }

}
