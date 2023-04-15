package com.eugene.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.mapper.MqTransactionLogMapper;
import com.eugene.pojo.MqTransactionLog;
import com.eugene.service.IMqTransactionLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author eugene
 */
@Service
public class MqTransactionLogServiceImpl extends ServiceImpl<MqTransactionLogMapper, MqTransactionLog> implements IMqTransactionLogService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T> boolean saveMQTransactionLog(String transactionId, T t) {
        System.out.println("saveMQTx " + JSONUtil.toJsonStr(t));
        MqTransactionLog log = new MqTransactionLog();
        log.setTransactionId(transactionId);
        log.setLog(JSONUtil.toJsonStr(t));
        try {
            this.save(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
