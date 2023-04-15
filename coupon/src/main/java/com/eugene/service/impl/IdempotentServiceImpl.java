package com.eugene.service.impl;

import com.eugene.common.enums.IdempotentStatusEnum;
import com.eugene.service.IdempotentService;
import com.eugene.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.eugene.common.constant.RedisKeyConstant.getIdempotentKey;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/13 15:01
 */
@Service
public class IdempotentServiceImpl implements IdempotentService {

    /**
     * 幂等key缓存存活时间, 1小时
     */
    private static final Long time = 60 * 60L;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean getIdempotentLock(String key) {
        return redisUtil.setnx(getIdempotentKey(key), IdempotentStatusEnum.IN_PROGRESS.getName(), time);
    }

    @Override
    public String getIdempotentResult(String key) {
        return (String) redisUtil.get(getIdempotentKey(key));
    }

    @Override
    public boolean setFinish(String key) {
        return redisUtil.set(getIdempotentKey(key), IdempotentStatusEnum.FINISH.getName(), 60 * 60);
    }


}
