package com.eugene.controller;

import com.eugene.controller.request.CouponTemplateRequest;
import com.eugene.response.Response;
import com.eugene.service.ICouponTemplateDoubleWriteService;
import com.eugene.service.ICouponTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Description 优惠券模版相关
 * @Author eugene
 * @Data 2023/4/4 14:53
 */
@RestController
@RequestMapping("/couponTemplate")
public class CouponTemplateController {
    private static final Logger log = LoggerFactory.getLogger(CouponTemplateController.class);

    @Autowired
    private ICouponTemplateService couponTemplateService;
    @Autowired
    private ICouponTemplateDoubleWriteService couponTemplateDoubleWriteService;

    @PostMapping("/addCouponTemplate")
    @Operation(summary = "添加优惠券模版信息", description = "添加优惠券模版信息")
    public Response addCouponTemplate(@RequestBody @Valid CouponTemplateRequest request) {
        return Response.success(couponTemplateService.addCouponTemplate(request));
        // 双写上线实战代码
//        return Response.success(couponTemplateDoubleWriteService.addCouponTemplateDoubleWrite(request));
    }

    @GetMapping("/getCouponTemplate")
    @Operation(summary = "查询优惠券模版信息", description = "添加优惠券模版信息")
    @Parameter(name = "couponTemplateCode", description = "券模版Code", required = true)
    public Response getCouponTemplate(@NotBlank(message = "券模版Code不能为空") @RequestParam("couponTemplateCode") String couponTemplateCode) {
        return Response.success(couponTemplateService.getCouponTemplate(couponTemplateCode));
        // 双写上线实战代码
//        log.info("mall-coupon getCouponTemplate couponTemplateCode:{}", couponTemplateCode);
//        return Response.success(couponTemplateDoubleWriteService.getCouponTemplateDoubleWrite(couponTemplateCode));

    }

}
