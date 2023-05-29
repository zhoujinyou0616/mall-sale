package com.eugene.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/17 12:40
 */
@Data
@TableName("t_activity")
@EqualsAndHashCode
public class Activity {

    @TableId(type = IdType.AUTO)
    @Schema(description = "id", required = true)
    private Long id;
    @Schema(description = "活动名称", required = true)
    private String name;
    @Schema(description = "活动价格", required = true)
    private BigDecimal price;
    @Schema(description = "活动库存", required = true)
    private Long stock;
    @Schema(description = "活动商品sku", required = true)
    private String sku;
    @Schema(description = "活动图片", required = true)
    private String image;
    @Schema(description = "活动详情", required = true)
    private String detail;
    @Schema(description = "活动预热时间", required = true)
    private Date preheatTime;
    @Schema(description = "活动开始时间", required = true)
    private Date beginTime;
    @Schema(description = "活动结束时间", required = true)
    private Date endTime;
    @Schema(description = "活动状态：0-未开始 1-进行中 2-已结束", required = true)
    private Integer status;
    @Schema(description = "活动启用状态：0-关闭 1-启用", required = true)
    private Integer enableStatus;
    @Schema(description = "活动类型：0-秒杀 1-拼团 2-砍价", required = true)
    private Integer type;
    @Schema(description = "'活动规则'", required = true)
    private String rule;
    @Schema(description = "活动创建人id", required = true)
    private Long createUserId;
    @Schema(description = "活动创建人手机号", required = true)
    private String createMobile;
    @Schema(description = "活动创建时间", required = true)
    private Date createTime;
    @Schema(description = "活动更新时间", required = true)
    private Date updateTime;


}
