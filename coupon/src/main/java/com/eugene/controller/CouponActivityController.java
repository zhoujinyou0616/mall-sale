package com.eugene.controller;

import com.eugene.controller.request.AddCouponActivityRequest;
import com.eugene.controller.request.CouponActivityRequest;
import com.eugene.controller.request.ReceiveCouponRequest;
import com.eugene.controller.request.UserCouponRequest;
import com.eugene.response.Response;
import com.eugene.service.ICouponActivityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description 优惠券活动相关
 * @Author eugene
 * @Data 2023/4/5 14:56
 */
@RestController
@RequestMapping("/couponActivity")
public class CouponActivityController {

    @Autowired
    private ICouponActivityService couponActivityService;


    @PostMapping("/addCouponActivity")
    @Operation(summary = "新建优惠券活动", description = "新建优惠券活动")
    public Response addCouponActivity(@RequestBody @Valid AddCouponActivityRequest request) {
        return Response.success(couponActivityService.addCouponActivity(request));
    }

    @PostMapping("/getCouponCenterList")
    @Operation(summary = "查询领券中心列表", description = "查询领券中心列表")
    public Response getCouponCenterList(@RequestBody @Valid UserCouponRequest request) {
        return Response.success(couponActivityService.getCouponCenterList(request));
    }

    @PostMapping("/getCouponActivityDetail")
    @Operation(summary = "查询领券活动详情", description = "查询领券活动详情")
    public Response getCouponActivityDetail(@RequestBody @Valid CouponActivityRequest request) {
        return Response.success(couponActivityService.getCouponActivityDetail(request));
    }

    @SneakyThrows
    @PostMapping("/receive")
    @Operation(summary = "领取优惠券", description = "领取优惠券")
    public Response receive(@RequestBody @Valid ReceiveCouponRequest request) {
        return Response.success(couponActivityService.receive(request));
    }


}
