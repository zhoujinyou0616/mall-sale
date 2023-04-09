package com.eugene.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 优惠券活动参与记录表
 * @Author eugene
 * @Data 2023/4/4 14:32
 */
@Data
@TableName("t_coupon_activity_log")
@EqualsAndHashCode
public class CouponActivityLog implements Serializable {
    private static final long serialVersionUID = -712335614171029202L;

    @TableId(type = IdType.AUTO)
    @Schema(description = "id", required = true)
    private Long id;
    @Schema(description = "券活动id", required = true)
    private Long couponActivityId;
    @Schema(description = "用户优惠券code", required = true)
    private String code;
    @Schema(description = "用户id", required = true)
    private Long userId;
    @Schema(description = "手机号", required = true)
    private Long mobile;
    @Schema(description = "创建时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Schema(description = "更新时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
