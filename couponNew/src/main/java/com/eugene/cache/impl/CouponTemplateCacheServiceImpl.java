package com.eugene.cache.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eugene.cache.ICouponTemplateCacheService;
import com.eugene.common.enums.StatusEnum;
import com.eugene.mapper.CouponTemplateMapper;
import com.eugene.pojo.CouponTemplate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.eugene.common.constant.RedisKeyConstant.getCouponTemplateCacheKey;

/**
 * @Description 券模版Guava缓存实现类
 * @Author eugene
 * @Data 2023/4/5 18:41
 */
@Service
public class CouponTemplateCacheServiceImpl implements ICouponTemplateCacheService {
    private static final Logger log = LoggerFactory.getLogger(CouponTemplateCacheServiceImpl.class);

    @Autowired
    private CouponTemplateMapper couponTemplateMapper;
    @Autowired
    private RedissonClient redisLockService;

    private LoadingCache<String, CouponTemplate> couponTemplateCache;

    @PostConstruct
    protected void init() {
        this.couponTemplateCache = CacheBuilder.newBuilder()
                .maximumSize(2 * 10000)
                .expireAfterWrite(3, TimeUnit.DAYS)
                .build(new CacheLoader<String, CouponTemplate>() {
                    @Override
                    public CouponTemplate load(@NotBlank String couponTemplateCode) throws Exception {
                        // 加锁防止缓存击穿
                        RLock lock = redisLockService.getLock(getCouponTemplateCacheKey(couponTemplateCode));
                        if (lock.tryLock()) {
                            try {
                                QueryWrapper<CouponTemplate> queryWrapper = new QueryWrapper<>();
                                queryWrapper.eq("code", couponTemplateCode);
                                CouponTemplate couponTemplateDB = couponTemplateMapper.selectOne(queryWrapper);
                                // 查询优惠券模版信息
                                if (Objects.isNull(couponTemplateDB)) {
                                    log.error("券模版信息不存在");
                                    return null;
                                }
                                return couponTemplateDB;
                            } finally {
                                if (lock.isLocked()) {
                                    // 严谨一点，防止当前线程释放掉其他线程的锁
                                    if (lock.isHeldByCurrentThread()) {
                                        lock.unlock();
                                    }
                                }
                            }
                        }
                        return null;
                    }
                });
        // 加载全部券模版信息到缓存中
//        loadAllCouponTemplateCache();

    }

    /**
     * 加载全部券模版信息到缓存中
     */
    private void loadAllCouponTemplateCache() {
        QueryWrapper<CouponTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", StatusEnum.AVAILABLE.getCode());
        List<CouponTemplate> couponTemplateList = couponTemplateMapper.selectList(queryWrapper);
        HashMap<String, CouponTemplate> couponTemplateHashMap = couponTemplateList.stream()
                .collect(Collectors.toMap(CouponTemplate::getCode, couponTemplate -> couponTemplate, (a, b) -> b, HashMap::new));
        couponTemplateCache.putAll(couponTemplateHashMap);
    }


    @Override
    public void setCouponTemplateCache(String couponTemplateCode, CouponTemplate couponTemplate) {
        couponTemplateCache.put(couponTemplateCode, couponTemplate);
    }

    @SneakyThrows
    @Override
    public CouponTemplate getCouponTemplateCache(String couponTemplateCode) {
        return couponTemplateCache.get(couponTemplateCode);
    }

    @Override
    public void invalidateCouponTemplateCache(String couponTemplateCode) {
        couponTemplateCache.invalidate(couponTemplateCode);
    }
}
