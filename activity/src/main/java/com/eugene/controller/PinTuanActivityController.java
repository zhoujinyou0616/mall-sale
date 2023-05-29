package com.eugene.controller;

import com.eugene.controller.request.CreatePinTuanActivityRequest;
import com.eugene.controller.request.JoinPinTuanActivityRequest;
import com.eugene.response.Response;
import com.eugene.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/5/8 22:02
 */
@RestController
@RequestMapping("/activity")
public class PinTuanActivityController {

    @Autowired
    private IActivityService activityService;

    /**
     * 查询活动详情
     */
    @GetMapping("/info/{sku}")
    public Response getActivityInfo(@PathVariable("sku") String sku) {
        return Response.success(activityService.getActivityInfoBySku(sku));
    }

    /**
     * 发起拼团活动
     */
    @PostMapping("/createPinTuanActivity")
    public Response createPinTuanActivity(@RequestBody CreatePinTuanActivityRequest request) {
        activityService.createPinTuanActivity(request);
        return Response.success();
    }

    /**
     * 查询拼团活动详情
     */
    @GetMapping("/pinTuanActivityInfo/{activityId}")
    public Response getPinTuanActivityInfo(@PathVariable("activityId") String activityId) {
        return Response.success(activityService.getPinTuanActivityInfo(activityId));
    }

    /**
     * 参加拼团活动
     */
    @PostMapping("/joinPinTuanActivity")
    public Response joinPinTuanActivity(@RequestBody JoinPinTuanActivityRequest request) {
        activityService.joinPinTuanActivity(request);
        return Response.success();
    }


}
