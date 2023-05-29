package com.eugene.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.common.enums.Errors;
import com.eugene.common.exception.BusinessException;
import com.eugene.controller.request.AdminActivityRequest;
import com.eugene.controller.response.AdminActivityResponse;
import com.eugene.design.strategy.IActivityStrategy;
import com.eugene.mapper.ActivityMapper;
import com.eugene.mq.producer.ActivityProducerService;
import com.eugene.pojo.Activity;
import com.eugene.service.IAdminActivityService;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.eugene.controller.request.AdminActivityRequest.converActivity;
import static com.eugene.utils.SpringContextUtils.applicationContext;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/18 15:44
 */
@Service
public class AdminActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IAdminActivityService {
    private static final Logger log = LoggerFactory.getLogger(AdminActivityServiceImpl.class);
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityProducerService activityProducerService;

    @Override
    public List<AdminActivityResponse> getActivityInfoList() {
        List<AdminActivityResponse> responseList = Lists.newArrayList();
        List<Activity> activityList = activityMapper.selectList(new QueryWrapper<>());
        if (CollectionUtil.isNotEmpty(activityList)) {
            responseList = activityList.stream().map(AdminActivityResponse::convertActivityResponse).collect(Collectors.toList());
        }
        return responseList;
    }

    @SneakyThrows
    @Override
    public boolean createActivity(AdminActivityRequest request) {
        // 检查活动设置是否正确
        if (Boolean.FALSE.equals(checkActivityConfig(request))) {
            log.error("活动设置不正确");
            throw new BusinessException(Errors.CREATE_ACTIVITY_CONFIG_ERROR.getCode(), Errors.CREATE_ACTIVITY_CONFIG_ERROR.getMsg());
        }
        Activity activity = converActivity(request);
        // 保存活动信息到数据库
        activityMapper.insert(activity);
        // 发送活动消息
        activityProducerService.delaySendMessage(activity);
        return true;
    }

    /**
     * 检查活动设置是否正确
     *
     * @param request
     * @return
     */
    private Boolean checkActivityConfig(AdminActivityRequest request) {
        // todo 检查活动时间、活动类型、活动规则、活动商品的时间、库存、价格等
        // 1、活动时间是否正确
        // 2、活动类型是否正确
        // 3、活动规则是否正确
        // 4、活动商品同一时间只能有一个活动，判断活动商品的活动时间是否有交集
        // 5、活动商品的库存是否正确
        // 6、活动商品的价格是否正确
        return true;
    }

    @Override
    public AdminActivityResponse getActivityInfo(Long activityId) {
        Activity activity = activityMapper.selectById(activityId);
        return AdminActivityResponse.convertActivityResponse(activity);
    }


    private IActivityStrategy getActivityStrategy(Integer type) {
        return applicationContext.getBean("activity_type_" + type, IActivityStrategy.class);
    }
}
