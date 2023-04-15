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
 * @Description 优惠券模版表
 * @Author eugene
 */
@Data
@TableName("t_coupon_template")
@EqualsAndHashCode
public class CouponTemplate implements Serializable {
    private static final long serialVersionUID = -490426673223213299L;
    @TableId(type = IdType.AUTO)
    @Schema(description = "id", required = true)
    private Long id;
    @Schema(description = "优惠券模版code", required = true)
    private String code;
    @Schema(description = "优惠券名称")
    private String name;
    @Schema(description = "优惠券金额", required = true)
    private Long price;
    @Schema(description = "优惠券使用门槛金额", required = true)
    private Long limitNumber;
    @Schema(description = "使用限制SKU")
    private String limitSku;
    @Schema(description = "优惠券名称")
    private String limitSpu;
    @Schema(description = "有效期类型：0-起止日期 1-有效天数", required = true)
    private Integer validityType;
    @Schema(description = "优惠券开始时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @Schema(description = "优惠券结束时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @Schema(description = "有效天数")
    private Long validityDay;
    @Schema(description = "优惠券模版状态：0-不可用 1-可用", required = true)
    private Integer status;
    @Schema(description = "创建时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Schema(description = "更新时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
