package com.eugene.controller;

import com.eugene.controller.request.SendCouponRequest;
import com.eugene.controller.request.UserCouponRequest;
import com.eugene.controller.request.VerificationCouponRequest;
import com.eugene.response.Response;
import com.eugene.service.ICouponService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/5 14:56
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private ICouponService couponService;

    @PostMapping("/getList")
    @Operation(summary = "查询用户优惠券列表", description = "查询用户优惠券列表")
    public Response getList(@RequestBody @Valid UserCouponRequest request) {
        return Response.success(couponService.getList(request));
    }

    @PostMapping("/send")
    @Operation(summary = "发放优惠券", description = "发放优惠券")
    public Response send(@RequestBody SendCouponRequest request) {
        return Response.success(couponService.send(request));
    }

    @GetMapping("/getCoupon")
    @Operation(summary = "查询优惠券信息", description = "查询优惠券信息")
    public Response getCoupon(@RequestParam("code") @NotBlank String code) {
        return Response.success(couponService.getCoupon(code));
    }

    @SneakyThrows
    @PostMapping("/verification")
    @Operation(summary = "去使用核销优惠券", description = "去使用核销优惠券")
    public Response verification(@RequestBody @Valid VerificationCouponRequest request) {
        return Response.success(couponService.verification(request));
    }

}
