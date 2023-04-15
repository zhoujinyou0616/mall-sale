package com.eugene.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/13 00:00
 */
@Data
@TableName("t_mq_transaction_log")
@EqualsAndHashCode
public class MqTransactionLog implements Serializable {

    @TableId(type = AUTO)
    @Schema(description = "id")
    private Long id;

    @Schema(description = "事务id")
    private String transactionId;

    @Schema(description = "日志")
    private String log;
}
