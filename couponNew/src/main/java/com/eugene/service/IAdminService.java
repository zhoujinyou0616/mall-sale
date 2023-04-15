package com.eugene.service;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/14 21:05
 */
public interface IAdminService {


    void moveCouponTemplate(String limitTime, Long startId);

    void checkCouponTemplate(Integer status);

    void repairCouponTemplate(List<String> couponTemplateCodes);
}
