package com.eugene.controller.request;

import com.eugene.common.enums.ActivityStatusEnum;
import com.eugene.pojo.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/18 15:34
 */
@Data
public class AdminActivityRequest {

    @Schema(description = "活动名称", required = true)
    @NotBlank(message = "活动名称不能为空")
    private String name;
    @Schema(description = "活动价格", required = true)
    @NotBlank(message = "活动价格不能为空")
    private String price;
    @Schema(description = "活动库存", required = true)
    @NotNull(message = "活动库存不能为空")
    private Long stock;
    @Schema(description = "活动商品sku", required = true)
    @NotBlank(message = "活动商品sku不能为空")
    private String sku;
    @Schema(description = "活动图片", required = true)
    @NotBlank(message = "活动图片不能为空")
    private String image;
    @Schema(description = "活动详情", required = true)
    @NotBlank(message = "活动详情不能为空")
    private String detail;
    @Schema(description = "活动预热时间", required = true)
    @NotNull(message = "活动预热时间不能为空")
    private Date preheatTime;
    @Schema(description = "活动开始时间", required = true)
    @NotNull(message = "活动开始时间不能为空")
    private Date beginTime;
    @Schema(description = "活动结束时间", required = true)
    @NotNull(message = "活动结束时间不能为空")
    private Date endTime;
    @Schema(description = "活动启用状态：0-关闭 1-启用", required = true)
    @NotNull(message = "活动启用类型不能为空")
    private Integer enableStatus;
    @Schema(description = "活动类型：0-秒杀 1-拼团 2-砍价", required = true)
    @NotNull(message = "活动类型不能为空")
    private Integer type;
    @Schema(description = "活动规则：{\\\"number\\\":2} number:成团人数", required = true)
    @NotBlank(message = "活动规则不能为空")
    private String rule;
    @Schema(description = "活动创建人id", required = true)
    @NotNull(message = "活动创建人id不能为空")
    private Long createUserId;
    @Schema(description = "活动创建人手机号", required = true)
    @NotBlank(message = "活动创建人手机号不能为空")
    private String createMobile;


    public static Activity converActivity(AdminActivityRequest request) {
        Activity activity = new Activity();
        activity.setName(request.getName());
        activity.setPrice(new BigDecimal(request.getPrice()));
        activity.setStock(request.getStock());
        activity.setImage(request.getImage());
        activity.setSku(request.getSku());
        activity.setDetail(request.getDetail());
        activity.setPreheatTime(request.getPreheatTime());
        activity.setBeginTime(request.getBeginTime());
        activity.setEndTime(request.getEndTime());
        // 根据时间判断活动状态
        activity.setStatus(getStatus(activity));
        activity.setEnableStatus(request.getEnableStatus());
        activity.setType(request.getType());
        activity.setRule(request.getRule());
        activity.setCreateUserId(request.getCreateUserId());
        activity.setCreateMobile(request.getCreateMobile());
        activity.setCreateTime(new Date());
        activity.setUpdateTime(new Date());
        return activity;
    }

    /**
     * 根据活动时间判断活动状态
     *
     * @param activity
     * @return
     */
    public static int getStatus(Activity activity) {
        Date currentDate = new Date();
        if (currentDate.before(activity.getPreheatTime())) {
            return ActivityStatusEnum.NO_START.getCode();
        } else if (currentDate.after(activity.getPreheatTime()) && currentDate.before(activity.getEndTime())) {
            return ActivityStatusEnum.IN_PROGRESS.getCode();
        } else if (currentDate.after(activity.getEndTime())) {
            return ActivityStatusEnum.FINISH.getCode();
        }
        return 0;
    }
}
