package com.eugene.mq.consumer;

import cn.hutool.json.JSONUtil;
import com.eugene.cache.IActivityCacheService;
import com.eugene.common.enums.IdempotentStatusEnum;
import com.eugene.mapper.ActivityMapper;
import com.eugene.mq.producer.ActivityProducerService;
import com.eugene.pojo.Activity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.eugene.common.constant.DelayMqConstant.DELAY_GROUP;
import static com.eugene.common.constant.DelayMqConstant.DELAY_TOPIC;
import static com.eugene.common.constant.RocketMqConstant.ACTIVITY_UPDATE_TAG;
import static com.eugene.controller.request.AdminActivityRequest.getStatus;

/**
 * @Description 活动消息监听类
 * @Author eugene
 * @Data 2023/4/12 11:37
 */
@Component
@RocketMQMessageListener(topic = DELAY_TOPIC, consumerGroup = DELAY_GROUP, selectorExpression = ACTIVITY_UPDATE_TAG)
public class ActivityMessageListener implements RocketMQListener<MessageExt> {

    private static final Logger log = LoggerFactory.getLogger(ActivityMessageListener.class);

    @Autowired
    private ActivityProducerService activityProducerService;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private IActivityCacheService activityCacheService;

    @SneakyThrows
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("sendRegisterUserCoupon 消息接收，msg:{}", JSONUtil.toJsonStr(messageExt));
        ObjectMapper objectMapper = new ObjectMapper();
        Activity activity = objectMapper.readValue(messageExt.getBody(), Activity.class);
        try {
            // 处理活动逻辑
            //1.活动状态为未开始，设置预热时间为活动延时消息的发送时间
            //2.活动状态为达到预热时间后，设置活动状态为进行中，设置活动缓存，设置活动结束时间为延时消息的发送时间
            //3.到达活动结束时间，设置活动状态为已结束，删除活动缓存
            int status = getStatus(activity);
            activity.setStatus(status);
            activity.setUpdateTime(new Date());
            // 保存数据库
            activityMapper.updateById(activity);
            // 保存活动缓存，todo 可以优化为本地缓存，进一步提高性能
            activityCacheService.setActivityCache(activity.getSku(), activity);
            // 判断活动状态，如果活动状态为未开始或者进行中，发送延时消息
            if (status == IdempotentStatusEnum.NO_START.getCode() || status == IdempotentStatusEnum.IN_PROGRESS.getCode()) {
                // 发送延时消息
                activityProducerService.delaySendMessage(activity);
            }
            // 判断活动状态，如果活动状态为已结束，删除活动缓存
            if (status == IdempotentStatusEnum.FINISH.getCode()) {
                activityCacheService.delActivityCache(activity.getSku());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
