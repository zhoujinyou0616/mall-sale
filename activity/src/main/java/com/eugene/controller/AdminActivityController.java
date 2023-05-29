package com.eugene.controller;

import com.eugene.controller.request.AdminActivityRequest;
import com.eugene.response.Response;
import com.eugene.service.IAdminActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/18 15:20
 */
@RestController
@RequestMapping("/adminActivity")
public class AdminActivityController {

    @Autowired
    private IAdminActivityService adminActivityService;

    @GetMapping("/getActivityList")
    public Response getActivityList() {
        return Response.success(adminActivityService.getActivityInfoList());
    }

    @PostMapping("/createActivity")
    public Response createActivity(@RequestBody @Valid AdminActivityRequest request) {
        return Response.success(adminActivityService.createActivity(request));
    }

    @GetMapping("/getActivity")
    public Response getActivity(@RequestParam("activityId") Long activityId) {
        return Response.success(adminActivityService.getActivityInfo(activityId));
    }


}
