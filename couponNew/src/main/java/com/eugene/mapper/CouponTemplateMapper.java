package com.eugene.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eugene.pojo.CouponTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/4 15:21
 */
@Repository
@DS("master")
public interface CouponTemplateMapper extends BaseMapper<CouponTemplate> {

    boolean saveBatch(@Param("couponTemplates") List<CouponTemplate> couponTemplates);

}
