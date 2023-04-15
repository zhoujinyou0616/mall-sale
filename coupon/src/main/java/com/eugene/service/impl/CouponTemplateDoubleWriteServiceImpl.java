package com.eugene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.common.config.HotConfig;
import com.eugene.common.enums.ReadAndWriteSwitchEnum;
import com.eugene.controller.request.CouponTemplateRequest;
import com.eugene.mapper.CouponTemplateMapper;
import com.eugene.pojo.CouponTemplate;
import com.eugene.service.ICouponTemplateDoubleWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 券模版相关实现类-双写阶段使用
 * @Author eugene
 * @Data 2023/4/4 15:20
 */
@Service
public class CouponTemplateDoubleWriteServiceImpl extends ServiceImpl<CouponTemplateMapper, CouponTemplate> implements ICouponTemplateDoubleWriteService {

    @Autowired
    private HotConfig hotConfig;
    @Resource
    @Qualifier("CouponTemplateServiceImpl")
    private CouponTemplateServiceImpl couponTemplateService;

    @Resource
    @Qualifier("NewCouponTemplateServiceImpl")
    private NewCouponTemplateServiceImpl newCouponTemplateService;

    @Override
    public boolean addCouponTemplateDoubleWrite(CouponTemplateRequest request) {
        String readAndWriteSwitch = hotConfig.getReadAndWriteSwitch();
        if (ReadAndWriteSwitchEnum.S0.getCode().equals(readAndWriteSwitch)) {
            couponTemplateService.addCouponTemplate(request);
        }
        if (ReadAndWriteSwitchEnum.S1.getCode().equals(readAndWriteSwitch)
                || ReadAndWriteSwitchEnum.S2.getCode().equals(readAndWriteSwitch)) {
            couponTemplateService.addCouponTemplate(request);
            newCouponTemplateService.addCouponTemplate(request);
        }
        if (ReadAndWriteSwitchEnum.S3.getCode().equals(readAndWriteSwitch)) {
            newCouponTemplateService.addCouponTemplate(request);
        }
        return true;
    }

    @Override
    public CouponTemplate getCouponTemplateDoubleWrite(String couponTemplateCode) {
        CouponTemplate couponTemplate = null;
        String readAndWriteSwitch = hotConfig.getReadAndWriteSwitch();
        if (ReadAndWriteSwitchEnum.S0.getCode().equals(readAndWriteSwitch) ||
                ReadAndWriteSwitchEnum.S1.getCode().equals(readAndWriteSwitch)) {
            couponTemplate = couponTemplateService.getCouponTemplate(couponTemplateCode);
        }
        if (ReadAndWriteSwitchEnum.S2.getCode().equals(readAndWriteSwitch) ||
                ReadAndWriteSwitchEnum.S3.getCode().equals(readAndWriteSwitch)) {
            couponTemplate = newCouponTemplateService.getCouponTemplate(couponTemplateCode);
        }
        return couponTemplate;
    }
}
