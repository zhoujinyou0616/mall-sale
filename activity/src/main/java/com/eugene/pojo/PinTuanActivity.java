package com.eugene.pojo;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eugene.common.enums.PinTuanStatusEnum;
import com.eugene.controller.request.CreatePinTuanActivityRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/18 15:03
 */
@Data
@TableName("t_pin_tuan_activity")
@EqualsAndHashCode
public class PinTuanActivity {

    @TableId(type = IdType.AUTO)
    @Schema(description = "id", required = true)
    private Long id;
    @Schema(description = "参与的拼团活动id", required = true)
    private Long joinPinTuanActivityId;
    @Schema(description = "活动id", required = true)
    private Long activityId;
    @Schema(description = "订单号", required = true)
    private String orderNo;
    @Schema(description = "活动商品sku")
    private String sku;
    @Schema(description = "参与用户id", required = true)
    private Long userId;
    @Schema(description = "手机号", required = true)
    private String mobile;
    @Schema(description = "拼团状态：0:待支付;1:已支付;2:已成团;3:未成团,已退款;4:超时未成团,已退款", required = true)
    private Integer status;
    @Schema(description = "创建时间", required = true)
    private Date createTime;
    @Schema(description = "更新时间", required = true)
    private Date updateTime;


    public static PinTuanActivity convertPinTuanActivity(CreatePinTuanActivityRequest request, Long activityId) {
        PinTuanActivity pinTuanActivity = new PinTuanActivity();
        pinTuanActivity.setActivityId(activityId);
        Snowflake snowflake = new Snowflake();
        pinTuanActivity.setOrderNo(snowflake.nextIdStr());
        pinTuanActivity.setSku(request.getSku());
        pinTuanActivity.setUserId(request.getUserId());
        pinTuanActivity.setMobile(request.getMobile());
        pinTuanActivity.setStatus(PinTuanStatusEnum.NO_PAY.getCode());
        pinTuanActivity.setCreateTime(new Date());
        pinTuanActivity.setUpdateTime(new Date());
        return pinTuanActivity;
    }

}
