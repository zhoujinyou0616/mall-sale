package com.eugene.mq.producer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.eugene.pojo.Activity;
import com.eugene.pojo.PinTuanActivity;
import lombok.SneakyThrows;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.eugene.common.constant.DelayMqConstant.DELAY_TOPIC;
import static com.eugene.common.constant.RocketMqConstant.ACTIVITY_UPDATE_TAG;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/12 10:24
 */
@Service
public class PinTuanActivityProducerService {
    private static final Logger log = LoggerFactory.getLogger(PinTuanActivityProducerService.class);
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    /**
     * 发送延时消息，设置定时消息和延时消息
     */
    @SneakyThrows
    public void delaySendMessage(PinTuanActivity pinTuanActivity) {
        Activity activityMessage = new Activity();
        BeanUtil.copyProperties(pinTuanActivity, activityMessage);
        Message message = new Message(DELAY_TOPIC, ACTIVITY_UPDATE_TAG, JSONUtil.toJsonStr(activityMessage).getBytes());
        // 设置消息发送时间， 1天
        message.setDelayTimeMs(1000 * 60 * 60 * 24);
        defaultMQProducer.send(message);
        log.info("delaySendMessage 消息发送， msg{}", JSONUtil.toJsonStr(message));
    }


}
