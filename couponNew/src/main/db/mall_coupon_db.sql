create
database IF NOT EXISTS `mall_coupon_new_db` default character set utf8mb4 collate utf8mb4_general_ci;

use
mall_coupon_new_db;

CREATE TABLE `t_coupon_template`
(
    `id`            bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `code`          varchar(30) NOT NULL COMMENT '优惠券模版code',
    `name`          varchar(20) NOT NULL COMMENT '优惠券名称',
    `price`         bigint(10) NOT NULL COMMENT '优惠券金额',
    `limit_number`  bigint(20) NOT NULL COMMENT '优惠券使用门槛金额',
    `limit_sku`     varchar(20) DEFAULT NULL COMMENT '使用限制SKU',
    `limit_spu`     varchar(20) DEFAULT NULL COMMENT '使用限制SPU',
    `validity_type` int2        NOT NULL COMMENT '有效期类型：0-起止日期 1-有效天数',
    `begin_time`    datetime    DEFAULT NULL COMMENT '优惠券开始时间',
    `end_time`      datetime    DEFAULT NULL COMMENT '优惠券结束时间',
    `validity_day`  int8        DEFAULT NULL COMMENT '有效天数',
    `status`        int2        NOT NULL COMMENT '优惠券模版状态：0-不可用 1-可用',
    `create_time`   datetime    NOT NULL COMMENT '创建时间',
    `update_time`   datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '优惠券模版表';
