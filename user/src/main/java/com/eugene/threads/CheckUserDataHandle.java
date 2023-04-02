package com.eugene.threads;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eugene.pojo.User;
import com.eugene.service.IUserShardingService;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/1 18:50
 */
public class CheckUserDataHandle implements Callable<List<Long>> {
    private IUserShardingService userShardingService;
    private List<User> users;


    public CheckUserDataHandle(IUserShardingService userShardingService, List<User> users) {
        this.userShardingService = userShardingService;
        this.users = users;
    }

    @Override
    public List<Long> call() {
        // 对比后差异的userIdList
        List<Long> diffUserIdList = new ArrayList<>();
        List<Long> userIdList = users.stream().map(User::getId).collect(Collectors.toList());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userIdList);
        List<User> userShardingList = userShardingService.list(queryWrapper);
        // 此处使用了Maps.newHashMapWithExpectedSize(),直接设置预期的容量,避免hashMap扩容带来的性能损耗
        HashMap<Long, User> userShardingMap = userShardingList.stream()
                .collect(Collectors.toMap(User::getId, user -> user, (a, b) -> b, () -> Maps.newHashMapWithExpectedSize(users.size())));
        for (User user : users) {
            // 对象不同时
            if (Objects.isNull(userShardingMap.get(user.getId()))
                    || !userShardingMap.get(user.getId()).equals(user)) {
                // 输出保存差异的userId
                diffUserIdList.add(user.getId());
            }
        }
        return diffUserIdList;
    }

}
