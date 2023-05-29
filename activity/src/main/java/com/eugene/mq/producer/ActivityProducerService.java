package com.eugene.mq.producer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.eugene.pojo.Activity;
import lombok.SneakyThrows;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.eugene.common.constant.DelayMqConstant.DELAY_TOPIC;
import static com.eugene.common.constant.RocketMqConstant.ACTIVITY_UPDATE_TAG;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/12 10:24
 */
@Service
public class ActivityProducerService {
    private static final Logger log = LoggerFactory.getLogger(ActivityProducerService.class);
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    /**
     * 发送延时消息，设置定时消息和延时消息
     *  todo 这里有个问题，rocketmq的消息发送时间仅支持3天内的时间，超过3天的时间会报错，需要优化
     *   这里给两个优化方案：
     *   1、业务手段，只允许创建3天内的活动时间，超过3天的活动时间不允许创建
     *   2、技术手段，超过3天的消息单独保存，使用定时任务每天扫描一次，当在3天内的时间时，再发送消息
     *   3、更好的方案待你完善...
     */
    @SneakyThrows
    public void delaySendMessage(Activity activity) {
        Activity activityMessage = new Activity();
        BeanUtil.copyProperties(activity, activityMessage);
        Message message = new Message(DELAY_TOPIC, ACTIVITY_UPDATE_TAG, JSONUtil.toJsonStr(activityMessage).getBytes());
        // 设置消息发送时间
        message.setDelayTimeMs(getDelaySendTimeMs(activity));
        defaultMQProducer.send(message);
        log.info("delaySendMessage 消息发送， msg{}", JSONUtil.toJsonStr(message));
    }

    /**
     * 根据当前时间判断延时开始时间
     *
     * @param activity
     * @return 返回时间差值毫秒数
     */
    public static long getDelaySendTimeMs(Activity activity) {
        Date currentDate = new Date();
        if (currentDate.before(activity.getPreheatTime())) {
            return DateUtil.between(currentDate, activity.getPreheatTime(), DateUnit.MS);
        } else if (currentDate.after(activity.getPreheatTime()) && currentDate.before(activity.getBeginTime())) {
            return DateUtil.between(currentDate, activity.getBeginTime(), DateUnit.MS);
        } else if (currentDate.after(activity.getBeginTime()) && currentDate.before(activity.getEndTime())) {
            return DateUtil.between(currentDate, activity.getEndTime(), DateUnit.MS);
        } else {
            return currentDate.getTime();
        }
    }

}
