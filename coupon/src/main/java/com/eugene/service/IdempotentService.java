package com.eugene.service;

/**
 * @Description 幂等校验
 * @Author eugene
 * @Data 2023/4/13 15:00
 */
public interface IdempotentService {

    boolean getIdempotentLock(String key);

    String getIdempotentResult(String valueOf);

    boolean setFinish(String key);
}
