package com.eugene.mq.producer;

import cn.hutool.json.JSONUtil;
import com.eugene.mq.vo.RegisterUserMessage;
import com.eugene.pojo.User;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.eugene.common.constant.RocketMqConstant.getRegisterTopicTag;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/12 10:24
 */
@Service
public class RegisterUserProducerService {
    private static final Logger log = LoggerFactory.getLogger(RegisterUserProducerService.class);
    @Resource
    public RocketMQTemplate rocketMQTemplate;
    @Value("${rocketmq.producer.send-message-timeout}")
    private Integer messageTimeOut;

    /**
     * 发送同步消息 （阻塞当前线程，等待broker响应发送结果，这样不太容易丢失消息）
     */
    public void syncSendRegisterMessage(User user) {
        RegisterUserMessage message = new RegisterUserMessage();
        message.setId(user.getId());
        message.setMobile(user.getMobile());
        message.setLevel(user.getLevel());
        message.setCreateTime(user.getCreateTime());
        rocketMQTemplate.syncSend(getRegisterTopicTag(), message);
        log.info("syncSendRegisterMessage 消息发送， msg{}", JSONUtil.toJsonStr(message));
    }

    /**
     * 发送异步消息（通过线程池执行发送到broker的消息任务，执行完后回调：在SendCallback中可处理相关成功失败时的逻辑）
     */
    public void asyncSendRegisterMessage(User user) {
        RegisterUserMessage message = new RegisterUserMessage();
        message.setId(user.getId());
        message.setMobile(user.getMobile());
        message.setLevel(user.getLevel());
        message.setCreateTime(user.getCreateTime());
        rocketMQTemplate.asyncSend(getRegisterTopicTag(), message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info(JSONUtil.toJsonStr(sendResult));
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("消息发送异常");
                throwable.printStackTrace();
            }
        });
        log.info("asyncSendRegisterMessage 消息发送， msg{}", JSONUtil.toJsonStr(message));
    }

    /**
     * 发送单向消息
     * 比较简单，发出去后，什么都不管直接返回
     */
    public void oneWaySendRegisterMessage(User user) {
        RegisterUserMessage message = new RegisterUserMessage();
        message.setId(user.getId());
        message.setMobile(user.getMobile());
        message.setLevel(user.getLevel());
        message.setCreateTime(user.getCreateTime());
        rocketMQTemplate.sendOneWay(getRegisterTopicTag(), message);
        log.info("oneWaySendRegisterMessage 消息发送， msg{}", JSONUtil.toJsonStr(message));
    }

    /**
     * 发送延时消息（上面的发送同步消息，delayLevel的值就为0，因为不延时）
     * 在start版本中 延时消息一共分为18个等级分别为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public void delaySendMessage(User user) {
        RegisterUserMessage message = new RegisterUserMessage();
        message.setId(user.getId());
        message.setMobile(user.getMobile());
        message.setLevel(user.getLevel());
        message.setCreateTime(user.getCreateTime());
        rocketMQTemplate.syncSend(getRegisterTopicTag(), MessageBuilder.withPayload(message).build(), messageTimeOut, 5);
        log.info("delaySendMessage 消息发送， msg{}", JSONUtil.toJsonStr(message));
    }


}
