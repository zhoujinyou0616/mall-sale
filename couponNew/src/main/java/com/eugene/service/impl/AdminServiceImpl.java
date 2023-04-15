package com.eugene.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eugene.pojo.CouponTemplate;
import com.eugene.remote.ICouponRemote;
import com.eugene.remote.request.CouponTemplateRemoteRequest;
import com.eugene.response.Response;
import com.eugene.service.IAdminService;
import com.eugene.service.ICouponTemplateService;
import com.eugene.threads.CheckCouponTemplateDataHandle;
import com.eugene.threads.SaveCouponTemplateThreadHandle;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/14 15:14
 */
@Service
public class AdminServiceImpl implements IAdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    public static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            20, 20, 300, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "thread_pool_coupon_handle_task_" + r.hashCode());
                }
                // 拒绝策略使用 CallerRunsPolicy，
            }, new ThreadPoolExecutor.CallerRunsPolicy()
    );


    @Autowired
    private ICouponTemplateService couponTemplateService;
    @Autowired
    private ICouponRemote couponRemote;


    @Override
    public void moveCouponTemplate(String limitTime, Long startId) {
        CouponTemplateRemoteRequest request = new CouponTemplateRemoteRequest();
        request.setLimitTime(limitTime);
        request.setLimitSize(1000L);
        while (true) {
            request.setStartId(startId);
            List<CouponTemplate> couponTemplateList = new ArrayList<>();
            Response response = couponRemote.list(request);
            if (ObjectUtil.isNotNull(response.getData())) {
                couponTemplateList = JSONUtil.toList(JSONUtil.toJsonStr(couponRemote.list(request).getData()), CouponTemplate.class);
            }
            if (CollectionUtil.isEmpty(couponTemplateList)) {
                log.info("迁移结束");
                return;
            }
            // 将数据保存到数据中，线程池处理耗时：
            threadPool.execute(new SaveCouponTemplateThreadHandle(couponTemplateService, couponTemplateList));
            // 更新当前执行id
            startId = couponTemplateList.get(couponTemplateList.size() - 1).getId();
        }
    }


    @SneakyThrows
    @Override
    public void checkCouponTemplate(Integer status) {
        // 检查数据差异，将不一致数据输出
        Long startId = 1L;
        List<String> diffCouponTemplateCodeList = new ArrayList<>();
        while (true) {
            List<CouponTemplate> couponTemplateList = new ArrayList<>();
            CouponTemplateRemoteRequest request = new CouponTemplateRemoteRequest();
            request.setStartId(startId);
            request.setLimitSize(1000L);
            Response response = couponRemote.list(request);
            if (ObjectUtil.isNotNull(response.getData())) {
                couponTemplateList = JSONUtil.toList(JSONUtil.toJsonStr(couponRemote.list(request).getData()), CouponTemplate.class);
            }
            if (CollectionUtil.isEmpty(couponTemplateList)) {
                break;
            }
            Future<List<String>> checkCouponTemplateDataFuture = threadPool.submit(new CheckCouponTemplateDataHandle(couponTemplateService, couponTemplateList));
            List<String> diffCodeList = checkCouponTemplateDataFuture.get(5, TimeUnit.MINUTES);
            if (CollectionUtil.isNotEmpty(diffCodeList)) {
                diffCouponTemplateCodeList.addAll(diffCodeList);
            }
            startId = couponTemplateList.get(couponTemplateList.size() - 1).getId();
        }
        // 对比数据耗时：
        System.out.println("差异结果 diffCouponTemplateCodeList = " + diffCouponTemplateCodeList);
        // 判断是否需要自动修复数据 status = 1 进行自动修复
        if (status.equals(1)) {
            repairCouponTemplate(diffCouponTemplateCodeList);
            System.out.println("纠正成功 = " + diffCouponTemplateCodeList);
        }
    }

    @Override
    public void repairCouponTemplate(List<String> couponTemplateCodes) {
        List<CouponTemplate> couponTemplateList = new ArrayList<>();
        CouponTemplateRemoteRequest request = new CouponTemplateRemoteRequest();
        request.setCodes(couponTemplateCodes);
        Response response = couponRemote.list(request);
        if (ObjectUtil.isNotNull(response.getData())) {
            couponTemplateList = JSONUtil.toList(JSONUtil.toJsonStr(couponRemote.list(request).getData()), CouponTemplate.class);
        }
        // 判断数据 是否存在， 存在就update，不存在就insert
        for (CouponTemplate couponTemplateRemote : couponTemplateList) {
            QueryWrapper<CouponTemplate> couponTemplateQueryWrapper = new QueryWrapper<>();
            couponTemplateQueryWrapper.eq("id", couponTemplateRemote.getId());
            CouponTemplate couponTemplate = couponTemplateService.getOne(couponTemplateQueryWrapper, false);
            if (Objects.isNull(couponTemplate)) {
                couponTemplateService.save(couponTemplateRemote);
            } else {
                couponTemplateService.updateById(couponTemplateRemote);
            }
        }
    }
}
