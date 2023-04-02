package com.eugene.service;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/1 15:13
 */
public interface IAdminService {

    void initUser();

    void moveShardingUsers(String limitTime, Long startUserId);

    void moveBatchShardingUsers(String limitTime, Long startUserId);

    void threadMoveBatchShardingUsers(String limitTime, Long startUserId);

    void checkUserData(Integer status);

    void repairUserData(List<Long> userIds);
}
