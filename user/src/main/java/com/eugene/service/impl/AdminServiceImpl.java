package com.eugene.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eugene.pojo.User;
import com.eugene.service.IAdminService;
import com.eugene.service.IUserService;
import com.eugene.service.IUserShardingService;
import com.eugene.threads.CheckUserDataHandle;
import com.eugene.threads.InitUserThreadHandle;
import com.eugene.threads.SaveShardingUserThreadHandle;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.eugene.utils.mobileUtil.randomMobile;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/1 15:14
 */
@Service
public class AdminServiceImpl implements IAdminService {

    public static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            30, 30, 300, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "thread_pool_user_handle_task_" + r.hashCode());
                }
            }, new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserShardingService userShardingService;


    @Override
    public void initUser() {
        List<User> userList = new ArrayList<>();
        Snowflake snowflake = new Snowflake();
        for (int j = 0; j < 10000; j++) {
            for (int i = 0; i < 1000; i++) {
                User user = new User();
                String mobile = randomMobile();
                user.setId(snowflake.nextId());
                user.setName("金金金金金金有" + mobile);
                user.setMobile(Long.valueOf(mobile));
                user.setLevel(1);
                user.setTags("10,12");
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                userList.add(user);
            }
            threadPool.execute(new InitUserThreadHandle(userService, userList));
            userList = new ArrayList<>();
        }
    }

    @Override
    public void moveShardingUsers(String limitTime, Long startUserId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 根据传入的时间来控制要迁移的数据
        // 查询当前最大的userId
        queryWrapper.select("max(id) as id").lt("create_time", limitTime);
        User endUser = userService.getOne(queryWrapper);
        System.out.println("endUserId: " + endUser.getId());
        // 默认从第一条开始
        while (endUser.getId() > startUserId) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.gt("id", startUserId)
                    .lt("create_time", limitTime)
                    .last("limit 1000");
            List<User> userList = userService.list(wrapper);
            // 将数据保存到分表后的数据中
            for (User user : userList) {
                // 1000万条数据，单线程预估消耗时间是30个小时，有兴趣可以自己跑一遍。
                userShardingService.save(user);
            }
            startUserId = userList.get(userList.size() - 1).getId();
        }
    }

    @Override
    public void moveBatchShardingUsers(String limitTime, Long startUserId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(id) as id").lt("create_time", limitTime);
        User endUser = userService.getOne(queryWrapper);
        // 根据传入的时间来控制要迁移的数据
        while (endUser.getId() > startUserId) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.gt("id", startUserId)
                    .lt("create_time", limitTime)
                    .last("limit 1000");
            List<User> userList = userService.list(wrapper);
            // 将查询的列表进行分组，方便数据批量保存到同一张表中
            Map<Long, List<User>> userListMap = userList.stream().collect(Collectors.groupingBy(user -> user.getMobile() % 8));
            // 将数据保存到分表后的数据中
            // 1000万条数据，单线程批量操作预估消耗时间是70分钟。
            for (List<User> users : userListMap.values()) {
                userShardingService.saveBatch(users);
            }
            startUserId = userList.get(userList.size() - 1).getId();
        }
    }

    @Override
    public void threadMoveBatchShardingUsers(String limitTime, Long startUserId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(id) as id").lt("create_time", limitTime);
        User endUser = userService.getOne(queryWrapper);
        // 根据传入的时间来控制要迁移的数据
        while (endUser.getId() > startUserId) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.gt("id", startUserId)
                    .lt("create_time", limitTime)
                    .last("limit 1000");
            List<User> userList = userService.list(wrapper);
            // 将查询的列表进行分组，方便数据批量保存到同一张表中
            Map<Long, List<User>> userListMap = userList.stream().collect(Collectors.groupingBy(user -> user.getMobile() % 8));
            // 将数据保存到分表后的数据中，线程池处理耗时：18分钟
            for (List<User> users : userListMap.values()) {
                threadPool.execute(new SaveShardingUserThreadHandle(userShardingService, users));
            }
            startUserId = userList.get(userList.size() - 1).getId();
        }
    }

    @SneakyThrows
    @Override
    public void checkUserData(Integer status) {
        // 检查数据差异，将不一致数据输出
        Long startUserId = 1L;
        List<Long> diffUserIdList = new ArrayList<>();
        List<User> userList;
        while (true) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.gt("id", startUserId)
                    .last("limit 1000");
            userList = userService.list(queryWrapper);
            if (CollectionUtil.isEmpty(userList)) {
                break;
            }
            Map<Long, List<User>> userListMap = userList.stream().collect(Collectors.groupingBy(user -> user.getId() % 8));
            for (List<User> users : userListMap.values()) {
                Future<List<Long>> checkUserDataFuture = threadPool.submit(new CheckUserDataHandle(userShardingService, users));
                List<Long> diffUserList = checkUserDataFuture.get(5, TimeUnit.MINUTES);
                if (CollectionUtil.isNotEmpty(diffUserList)) {
                    diffUserIdList.addAll(diffUserList);
                }
            }
            startUserId = userList.get(userList.size() - 1).getId();
        }
        // 对比数据耗时：46分钟
        System.out.println("差异结果 diffUserIdList = " + diffUserIdList);
        // 判断是否需要自动修复数据 status = 1 进行自动修复
        if (status.equals(1)) {
            repairUserData(diffUserIdList);
            System.out.println("纠正成功 = " + diffUserIdList);
        }
    }

    @Override
    public void repairUserData(List<Long> userIds) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userIds);
        List<User> userList = userService.list(queryWrapper);
        // 判断数据 是否存在， 存在就update，不存在就insert
        for (User user : userList) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("id", user.getId());
            // mobile为分片键，所以设置null不更新
            User userShardingDB = userShardingService.getOne(userQueryWrapper, false);
            if (Objects.isNull(userShardingDB)) {
                userShardingService.save(user);
            } else {
                user.setMobile(null);
                userShardingService.updateById(user);
            }
        }
    }

}
