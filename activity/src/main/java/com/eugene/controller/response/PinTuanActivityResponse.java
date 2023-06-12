package com.eugene.controller.response;

import com.eugene.pojo.PinTuanActivity;
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
@EqualsAndHashCode
public class PinTuanActivityResponse {

    @Schema(description = "id")
    private Long id;
    @Schema(description = "参与的拼团活动id", required = true)
    private Long joinPinTuanActivityId;
    @Schema(description = "活动id")
    private Long activityId;
    @Schema(description = "订单号")
    private String orderNo;
    @Schema(description = "活动商品sku")
    private String sku;
    @Schema(description = "参与用户id")
    private Long userId;
    @Schema(description = "手机号")
    private String mobile;
    @Schema(description = "拼团状态：0:待支付;1:已支付;2:已成团;3:未成团,已退款;4:超时未成团,已退款")
    private Integer status;
    @Schema(description = "创建时间")
    private Date createTime;
    @Schema(description = "更新时间")
    private Date updateTime;


    public static PinTuanActivityResponse convertPinTuanActivityResponse(PinTuanActivity pinTuanActivityInfo) {
        PinTuanActivityResponse response = new PinTuanActivityResponse();
        response.setId(pinTuanActivityInfo.getId());
        response.setJoinPinTuanActivityId(pinTuanActivityInfo.getJoinPinTuanActivityId());
        response.setActivityId(pinTuanActivityInfo.getActivityId());
        response.setOrderNo(pinTuanActivityInfo.getOrderNo());
        response.setSku(pinTuanActivityInfo.getSku());
        response.setUserId(pinTuanActivityInfo.getUserId());
        response.setMobile(pinTuanActivityInfo.getMobile());
        response.setStatus(pinTuanActivityInfo.getStatus());
        response.setCreateTime(pinTuanActivityInfo.getCreateTime());
        response.setUpdateTime(pinTuanActivityInfo.getUpdateTime());
        return response;
    }
}
