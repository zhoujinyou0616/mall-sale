package com.eugene.remote.impl;

import com.eugene.controller.request.CouponTemplateRequest;
import com.eugene.remote.ICouponRemote;
import com.eugene.remote.request.CouponTemplateRemoteRequest;
import com.eugene.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/14 23:21
 */
@Component
public class CouponRemoteHystrix implements FallbackFactory<ICouponRemote> {
    private static final Logger log = LoggerFactory.getLogger(CouponRemoteHystrix.class);

    @Override
    public ICouponRemote create(Throwable cause) {
        return new ICouponRemote() {
            @Override
            public Response list(CouponTemplateRemoteRequest request) {
                log.error("list hystrix", cause);
                return null;
            }

            @Override
            public Response addCouponTemplate(CouponTemplateRequest request) {
                log.error("addCouponTemplate hystrix", cause);
                return null;
            }

            @Override
            public Response getCouponTemplate(String couponTemplateCode) {
                log.error("getCouponTemplate hystrix", cause);
                return null;
            }
        };
    }
}
