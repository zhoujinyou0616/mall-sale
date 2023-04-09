package com.eugene.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eugene.pojo.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/5 15:02
 */
@Repository
@DS("shardingmaster")
public interface CouponMapper extends BaseMapper<Coupon> {
    Boolean saveBatch(@Param("coupons") List<Coupon> coupons);

}
