package com.eugene.mq.producer;

import cn.hutool.json.JSONUtil;
import com.eugene.pojo.MqTransactionLog;
import com.eugene.pojo.User;
import com.eugene.service.IMqTransactionLogService;
import com.eugene.service.IUserShardingService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Objects;


/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/13 10:22
 */
@RocketMQTransactionListener
public class TXRegisterLocalListener implements RocketMQLocalTransactionListener {
    private static final Logger log = LoggerFactory.getLogger(TXRegisterLocalListener.class);

    @Autowired
    private IUserShardingService userShardingService;
    @Autowired
    private IMqTransactionLogService mqTransactionLogService;


    /**
     * 执行本地事务方法
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object obj) {
        MessageHeaders headers = msg.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        log.info("执行本地事务 transactionId:{}", transactionId);
        // 执行事务方法
        try {
            MqTransactionLog mqTransactionLog = (MqTransactionLog) obj;
            User user = JSONUtil.toBean(mqTransactionLog.getLog(), User.class);
            // 保存用户信息
            userShardingService.save(user);
            // 保存mq日志
            mqTransactionLogService.saveMQTransactionLog(transactionId, user);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("执行本地事务 异常，transactionId:{}", transactionId);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 检查本地事务执行结果
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        MessageHeaders headers = msg.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        log.info("回查本地事务 transactionId:{}", transactionId);

        MqTransactionLog mqTransactionLog = mqTransactionLogService.getById(transactionId);
        // 没查到本地事务，执行失败，通知回滚
        if (Objects.isNull(mqTransactionLog)) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        // 执行成功
        return RocketMQLocalTransactionState.COMMIT;
    }
}
