package com.eugene.remote;

import com.eugene.controller.request.CouponTemplateRequest;
import com.eugene.remote.impl.CouponRemoteHystrix;
import com.eugene.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @Description 调用优惠券服务操作类
 * @Author eugene
 * @Data 2023/4/14 22:43
 */

/**
 * name：指定注册中心服务名，如果调用的服务没有接入注册中心， 可以通过url的方式调用
 * path：调用接口controller指定的@RequestMapping路径
 */
//@FeignClient(url = "http:127.0.0.1:8081")
@FeignClient(name = "mall-coupon-new", path = "/couponNew", fallbackFactory = CouponRemoteHystrix.class)
public interface ICouponRemote {

    @PostMapping("/couponTemplate/addCouponTemplate")
    Response addCouponTemplate(@RequestBody @Valid CouponTemplateRequest request);

    @GetMapping("/couponTemplate/getCouponTemplate")
    Response getCouponTemplate(@RequestParam("couponTemplateCode") String couponTemplateCode);
}
