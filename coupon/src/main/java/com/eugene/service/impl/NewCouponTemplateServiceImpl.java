package com.eugene.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.eugene.controller.request.CouponTemplateRemoteRequest;
import com.eugene.controller.request.CouponTemplateRequest;
import com.eugene.pojo.CouponTemplate;
import com.eugene.remote.ICouponRemote;
import com.eugene.response.Response;
import com.eugene.response.ResponseConstants;
import com.eugene.service.ICouponTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 券模版相关实现类
 * @Author eugene
 * @Data 2023/4/4 15:20
 */
@Service("NewCouponTemplateServiceImpl")
public class NewCouponTemplateServiceImpl implements ICouponTemplateService {

    @Autowired
    private ICouponRemote couponRemote;

    @Override
    public boolean addCouponTemplate(CouponTemplateRequest request) {
        Response response = couponRemote.addCouponTemplate(request);
        if (ResponseConstants.SUCCESS_CODE.equals(response.getCode())) {
            return true;
        }
        return false;
    }

    @Override
    public CouponTemplate getCouponTemplate(String couponTemplateCode) {
        Response response = couponRemote.getCouponTemplate(couponTemplateCode);
        if (ObjectUtil.isNotNull(response.getData())) {
            return JSONUtil.toBean(JSONUtil.toJsonStr(response.getData()), CouponTemplate.class);
        }
        return null;
    }

    @Override
    @Deprecated
    public List<CouponTemplate> list(CouponTemplateRemoteRequest request) {
        return null;
    }
}
