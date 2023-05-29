create
database IF NOT EXISTS `mall_activity_db` default character set utf8mb4 collate utf8mb4_general_ci;

use
mall_activity_db;

CREATE TABLE `t_activity`
(
    `id`             bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `name`           varchar(20)    NOT NULL COMMENT '活动名称',
    `price`          decimal(15, 2) NOT NULL COMMENT '活动价格',
    `stock`          int8           NOT NULL COMMENT '活动库存',
    `sku`            varchar(50)    NOT NULL COMMENT '活动商品sku',
    `image`          varchar(500)   NOT NULL COMMENT '活动主图',
    `detail`         varchar(1000)  NOT NULL COMMENT '活动详情',
    `preheat_time`   datetime       NOT NULL COMMENT '活动预热时间',
    `begin_time`     datetime       NOT NULL COMMENT '活动开始时间',
    `end_time`       datetime       NOT NULL COMMENT '活动结束时间',
    `status`         int2           NOT NULL COMMENT '活动状态：0-未开始 1-进行中 2-已结束',
    `enable_status`  int2           NOT NULL COMMENT '活动启用状态：0-关闭 1-启用',
    `type`           int2           NOT NULL COMMENT '活动类型：0-秒杀 1-拼团 2-砍价',
    `rule`           varchar(500)   NOT NULL COMMENT '活动规则',
    `create_user_id` bigint(20) NOT NULL COMMENT '活动创建人',
    `create_mobile`  bigint(20) NOT NULL COMMENT '活动创建人手机号',
    `create_time`    datetime       NOT NULL COMMENT '创建时间',
    `update_time`    datetime       NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '活动表';


CREATE TABLE `t_pin_tuan_activity_log`
(
    `id`          bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `activity_id` bigint(20) NOT NULL COMMENT '活动id',
    `order_no`    varchar(20) NOT NULL COMMENT '订单号',
    `user_id`     bigint(20) NOT NULL COMMENT '参与用户id',
    `mobile`      varchar(20) NOT NULL COMMENT '手机号',
    `status`      int2        NOT NULL COMMENT '拼团状态：0:待支付;1:已支付;2:已成团;3:未成团,已退款;4:超时未成团,已退款',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `update_time` datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '拼团活动参与记录表';
