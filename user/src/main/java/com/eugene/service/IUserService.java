package com.eugene.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eugene.pojo.User;

import java.util.List;

/**
 * 后续所有用户相关方法调用 IUserShardingService
 * todo 分表迁移完成后 可以删除此类
 */
@Deprecated
public interface IUserService extends IService<User> {

    List<User> list(QueryWrapper<User> queryWrapper);

    boolean save(User user);

    /**
     * 批量保存用户信息
     *
     * @param userList
     * @return
     */
    Boolean saveBatch(List<User> userList);
}