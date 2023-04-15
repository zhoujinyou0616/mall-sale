package com.eugene.mq.producer;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.eugene.mq.vo.RegisterUserMessage;
import com.eugene.pojo.MqTransactionLog;
import com.eugene.pojo.User;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.eugene.common.constant.RocketMqConstant.getTxRegisterTopicTag;

/**
 * @Description 分布式事务
 * @Author eugene
 * @Data 2023/4/12 22:24
 */
@Service
public class TxRegisterUserProducerService {
    private static final Logger log = LoggerFactory.getLogger(TxRegisterUserProducerService.class);
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 先向MQ Server发送半消息
     */
    public TransactionSendResult sendHalfMessage(User user) {
        RegisterUserMessage message = new RegisterUserMessage();
        message.setId(user.getId());
        message.setMobile(user.getMobile());
        message.setLevel(user.getLevel());
        message.setCreateTime(user.getCreateTime());
        // 生成分布式事务id
        Snowflake snowflake = new Snowflake();
        long transactionId = snowflake.nextId();
        log.info("发送半消息 transactionId:{}", transactionId);
        MqTransactionLog transactionLog = new MqTransactionLog();
        transactionLog.setTransactionId(String.valueOf(transactionId));
        transactionLog.setLog(JSONUtil.toJsonStr(user));

        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(getTxRegisterTopicTag(),
                MessageBuilder.withPayload(message)
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                        .build(),
                transactionLog);
        log.info("发送半消息 sendResult:{}", JSONUtil.toJsonStr(sendResult));
        return sendResult;
    }

}
