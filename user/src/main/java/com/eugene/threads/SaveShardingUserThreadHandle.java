package com.eugene.threads;

import com.eugene.pojo.User;
import com.eugene.service.IUserShardingService;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/1 00:45
 */
public class SaveShardingUserThreadHandle implements Runnable {
    private IUserShardingService userShardingService;

    private List<User> users;

    public SaveShardingUserThreadHandle(IUserShardingService userShardingService, List<User> users) {
        this.userShardingService = userShardingService;
        this.users = users;
    }

    @Override
    public void run() {
        userShardingService.saveBatch(users);
    }
}
