package com.eugene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eugene.pojo.MqTransactionLog;

public interface IMqTransactionLogService extends IService<MqTransactionLog> {


    <T> boolean saveMQTransactionLog(String transactionId, T t);


}