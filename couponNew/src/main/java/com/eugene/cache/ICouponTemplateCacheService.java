package com.eugene.cache;

import com.eugene.pojo.CouponTemplate;

/**
 * @Description 券模版Guava缓存
 * @Author eugene
 * @Data 2023/4/5 18:38
 */
public interface ICouponTemplateCacheService {

    void setCouponTemplateCache(String couponTemplateCode, CouponTemplate couponTemplate);

    CouponTemplate getCouponTemplateCache(String couponTemplateCode);

    void invalidateCouponTemplateCache(String couponTemplateCode);


}
