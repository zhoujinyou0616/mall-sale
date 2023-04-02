package com.eugene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eugene.pojo.User;

import java.util.List;

public interface IUserShardingService extends IService<User> {

    boolean save(User user);

    /**
     * 批量保存用户信息
     *
     * @param userList
     * @return
     */
    Boolean saveBatch(List<User> userList);

}