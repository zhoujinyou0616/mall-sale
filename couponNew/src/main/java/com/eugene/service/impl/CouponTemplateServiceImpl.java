package com.eugene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.cache.ICouponTemplateCacheService;
import com.eugene.common.enums.StatusEnum;
import com.eugene.controller.request.CouponTemplateRequest;
import com.eugene.mapper.CouponTemplateMapper;
import com.eugene.pojo.CouponTemplate;
import com.eugene.service.ICouponTemplateService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.eugene.utils.CouponUtil.getCouponTemplateCode;

/**
 * @Description 券模版相关实现类
 * @Author eugene
 * @Data 2023/4/4 15:20
 */
@Service
public class CouponTemplateServiceImpl extends ServiceImpl<CouponTemplateMapper, CouponTemplate> implements ICouponTemplateService {

    @Autowired
    private CouponTemplateMapper couponTemplateMapper;
    @Autowired
    private ICouponTemplateCacheService couponTemplateCacheService;

    @Override
    public boolean addCouponTemplate(CouponTemplateRequest request) {
        CouponTemplate couponTemplate = new CouponTemplate();
        // todo 双写阶段的code应该取旧服务传过来的,否则就自动生成code
//        couponTemplate.setCode(getCouponTemplateCode());
        couponTemplate.setCode(StringUtil.isNotBlank(request.getCode()) ? request.getCode() : getCouponTemplateCode());
        couponTemplate.setName(request.getName());
        couponTemplate.setPrice(request.getPrice());
        couponTemplate.setLimitNumber(request.getLimitNumber());
        couponTemplate.setLimitSku(request.getLimitSku());
        couponTemplate.setLimitSpu(request.getLimitSpu());
        couponTemplate.setValidityType(request.getValidityType());
        couponTemplate.setBeginTime(request.getBeginTime());
        couponTemplate.setEndTime(request.getEndTime());
        couponTemplate.setValidityDay(request.getValidityDay());
        couponTemplate.setStatus(StatusEnum.AVAILABLE.getCode());
        couponTemplate.setCreateTime(new Date());
        couponTemplate.setUpdateTime(new Date());
        int result = couponTemplateMapper.insert(couponTemplate);
        if (result > 0) {
            // 保存到Guava缓存中
            couponTemplateCacheService.setCouponTemplateCache(couponTemplate.getCode(), couponTemplate);
        }
        return result > 0;
    }

    @Override
    public CouponTemplate getCouponTemplate(String couponTemplateCode) {
        return couponTemplateCacheService.getCouponTemplateCache(couponTemplateCode);
    }

    @Override
    public boolean saveBatch(List<CouponTemplate> couponTemplateList) {
        return couponTemplateMapper.saveBatch(couponTemplateList);
    }
}
