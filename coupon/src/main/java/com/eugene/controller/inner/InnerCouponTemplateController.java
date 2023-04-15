package com.eugene.controller.inner;

import com.eugene.controller.request.CouponTemplateRemoteRequest;
import com.eugene.response.Response;
import com.eugene.service.ICouponTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 优惠券模版相关
 * @Author eugene
 * @Data 2023/4/4 14:53
 */
@RestController
@RequestMapping("/inner/couponTemplate")
public class InnerCouponTemplateController {

    @Autowired
    private ICouponTemplateService couponTemplateService;

    @PostMapping("/list")
    public Response list(@RequestBody CouponTemplateRemoteRequest request) {
        return Response.success(couponTemplateService.list(request));
    }


}
