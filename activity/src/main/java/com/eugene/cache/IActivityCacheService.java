
package com.eugene.cache;


import com.eugene.pojo.Activity;

/**
 * TODO 可以优化成guava cache，下面直接增加实现类即可
 *
 * @Author eugene
 * @Data 2023/4/27 20:43
 */
public interface IActivityCacheService {

    void setActivityCache(String sku, Activity activity);

    Activity getActivityCache(String sku);

    void delActivityCache(String sku);
}