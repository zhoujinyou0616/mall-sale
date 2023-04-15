package com.eugene.mq.consumer;

import cn.hutool.json.JSONUtil;
import com.eugene.common.enums.IdempotentStatusEnum;
import com.eugene.common.exception.BusinessException;
import com.eugene.controller.request.SendCouponRequest;
import com.eugene.mq.vo.RegisterUserMessage;
import com.eugene.service.ICouponService;
import com.eugene.service.IdempotentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.eugene.common.constant.RocketMqConstant.*;

/**
 * @Description 用户注册消息监听类 事务
 * @Author eugene
 * @Data 2023/4/12 11:37
 */
@Component
@RocketMQMessageListener(topic = MALL_USER_TOPIC, consumerGroup = TX_REGISTER_GROUP, selectorExpression = TX_REGISTER_TAG)
public class TxRegisterUserSendCouponListener implements RocketMQListener<MessageExt> {

    private static final Logger log = LoggerFactory.getLogger(TxRegisterUserSendCouponListener.class);

    @Autowired
    private ICouponService couponService;
    @Autowired
    private IdempotentService idempotentService;

    @SneakyThrows
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("txSendRegisterUserCoupon 消息接收，msg:{}", JSONUtil.toJsonStr(messageExt));
        String msgId = messageExt.getMsgId();
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterUserMessage registerUserMessage = objectMapper.readValue(messageExt.getBody(), RegisterUserMessage.class);
        // 幂等判断消息是否重复消费
        if (Boolean.FALSE.equals(idempotentService.getIdempotentLock(String.valueOf(msgId)))) {
            // 命中幂等，查询当前处理进度
            String idempotentResult = idempotentService.getIdempotentResult(String.valueOf(msgId));
            if (StringUtils.isNotBlank(idempotentResult)) {
                // 如果处理中，则丢回消息队列重试，
                if (IdempotentStatusEnum.isInProgress(idempotentResult)) {
                    throw new BusinessException("sendRegisterUserCoupon MQ error, msg:{}", JSONUtil.toJsonStr(registerUserMessage));
                }
                // 如果处理完成直接返回处理结果，
                if (IdempotentStatusEnum.isFinish(idempotentResult)) {
                    // todo 返回处理结果
                    return;
                }
            }
        }
        // 没有命中幂等，直接处理业务逻辑
        try {
            SendCouponRequest request = new SendCouponRequest();
            request.setUserId(registerUserMessage.getId());
            request.setMobile(registerUserMessage.getMobile());
            // todo 查询用户发券活动配置
            request.setCouponTemplateCode("CP1644549976601587712");
            request.setNumber(1);
            couponService.send(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // 处理完成后，设置处理状态为 已完成
        idempotentService.setFinish(String.valueOf(msgId));
        System.out.println("消费消息: " + JSONUtil.toJsonStr(messageExt));
    }

}
