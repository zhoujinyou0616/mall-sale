package com.eugene.controller.response;

import com.eugene.pojo.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/20 22:11
 */
@Data
public class ActivityResponse {

    @Schema(description = "id")
    private Long id;
    @Schema(description = "活动名称")
    private String name;
    @Schema(description = "活动价格")
    private BigDecimal price;
    @Schema(description = "活动库存")
    private Long stock;
    @Schema(description = "活动商品sku")
    private String sku;
    @Schema(description = "活动图片")
    private String image;
    @Schema(description = "活动详情")
    private String detail;
    @Schema(description = "活动预热时间")
    private Date preheatTime;
    @Schema(description = "活动开始时间")
    private Date beginTime;
    @Schema(description = "活动结束时间")
    private Date endTime;
    @Schema(description = "活动状态：0-未开始 1-进行中 2-已结束")
    private Integer status;
    @Schema(description = "活动类型：0-秒杀 1-拼团 2-砍价")
    private Integer type;
    @Schema(description = "活动创建时间")
    private Date createTime;

    public static ActivityResponse convertActivityResponse(Activity activity) {
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setName(activity.getName());
        response.setPrice(activity.getPrice());
        response.setStock(activity.getStock());
        response.setSku(activity.getSku());
        response.setImage(activity.getImage());
        response.setDetail(activity.getDetail());
        response.setPreheatTime(activity.getPreheatTime());
        response.setBeginTime(activity.getBeginTime());
        response.setEndTime(activity.getEndTime());
        response.setStatus(activity.getStatus());
        response.setType(activity.getType());
        response.setCreateTime(activity.getCreateTime());
        return response;
    }

}
