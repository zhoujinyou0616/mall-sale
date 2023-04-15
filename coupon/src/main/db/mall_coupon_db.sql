create
database IF NOT EXISTS `mall_coupon_db` default character set utf8mb4 collate utf8mb4_general_ci;

use
mall_coupon_db;

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


CREATE TABLE `t_coupon0`
(
    `id`                   bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `coupon_template_code` varchar(30) NOT NULL COMMENT '优惠券模版code',
    `code`                 varchar(30) NOT NULL COMMENT '用户优惠券code',
    `user_id`              bigint(20) NOT NULL COMMENT '用户id',
    `mobile`               bigint(20) NOT NULL COMMENT '手机号',
    `status`               int2        NOT NULL COMMENT '优惠券状态：0-不可用 1-可用 2-已使用 3-已过期',
    `begin_time`           datetime    NOT NULL COMMENT '优惠券开始时间',
    `end_time`             datetime    NOT NULL COMMENT '优惠券结束时间',
    `create_time`          datetime    NOT NULL COMMENT '优惠券领取时间',
    `use_time`             datetime DEFAULT NULL COMMENT '优惠券使用时间',
    `update_time`          datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '用户优惠券表';

CREATE TABLE `t_coupon1`
(
    `id`                   bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `coupon_template_code` varchar(30) NOT NULL COMMENT '优惠券模版code',
    `code`                 varchar(30) NOT NULL COMMENT '用户优惠券code',
    `user_id`              bigint(20) NOT NULL COMMENT '用户id',
    `mobile`               bigint(20) NOT NULL COMMENT '手机号',
    `status`               int2        NOT NULL COMMENT '优惠券状态：0-不可用 1-可用 2-已使用 3-已过期',
    `begin_time`           datetime    NOT NULL COMMENT '优惠券开始时间',
    `end_time`             datetime    NOT NULL COMMENT '优惠券结束时间',
    `create_time`          datetime    NOT NULL COMMENT '优惠券领取时间',
    `use_time`             datetime DEFAULT NULL COMMENT '优惠券使用时间',
    `update_time`          datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '用户优惠券表';

CREATE TABLE `t_coupon2`
(
    `id`                   bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `coupon_template_code` varchar(30) NOT NULL COMMENT '优惠券模版code',
    `code`                 varchar(30) NOT NULL COMMENT '用户优惠券code',
    `user_id`              bigint(20) NOT NULL COMMENT '用户id',
    `mobile`               bigint(20) NOT NULL COMMENT '手机号',
    `status`               int2        NOT NULL COMMENT '优惠券状态：0-不可用 1-可用 2-已使用 3-已过期',
    `begin_time`           datetime    NOT NULL COMMENT '优惠券开始时间',
    `end_time`             datetime    NOT NULL COMMENT '优惠券结束时间',
    `create_time`          datetime    NOT NULL COMMENT '优惠券领取时间',
    `use_time`             datetime DEFAULT NULL COMMENT '优惠券使用时间',
    `update_time`          datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '用户优惠券表';

CREATE TABLE `t_coupon3`
(
    `id`                   bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `coupon_template_code` varchar(30) NOT NULL COMMENT '优惠券模版code',
    `code`                 varchar(30) NOT NULL COMMENT '用户优惠券code',
    `user_id`              bigint(20) NOT NULL COMMENT '用户id',
    `mobile`               bigint(20) NOT NULL COMMENT '手机号',
    `status`               int2        NOT NULL COMMENT '优惠券状态：0-不可用 1-可用 2-已使用 3-已过期',
    `begin_time`           datetime    NOT NULL COMMENT '优惠券开始时间',
    `end_time`             datetime    NOT NULL COMMENT '优惠券结束时间',
    `create_time`          datetime    NOT NULL COMMENT '优惠券领取时间',
    `use_time`             datetime DEFAULT NULL COMMENT '优惠券使用时间',
    `update_time`          datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '用户优惠券表';

CREATE TABLE `t_coupon4`
(
    `id`                   bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `coupon_template_code` varchar(30) NOT NULL COMMENT '优惠券模版code',
    `code`                 varchar(30) NOT NULL COMMENT '用户优惠券code',
    `user_id`              bigint(20) NOT NULL COMMENT '用户id',
    `mobile`               bigint(20) NOT NULL COMMENT '手机号',
    `status`               int2        NOT NULL COMMENT '优惠券状态：0-不可用 1-可用 2-已使用 3-已过期',
    `begin_time`           datetime    NOT NULL COMMENT '优惠券开始时间',
    `end_time`             datetime    NOT NULL COMMENT '优惠券结束时间',
    `create_time`          datetime    NOT NULL COMMENT '优惠券领取时间',
    `use_time`             datetime DEFAULT NULL COMMENT '优惠券使用时间',
    `update_time`          datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '用户优惠券表';

CREATE TABLE `t_coupon5`
(
    `id`                   bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `coupon_template_code` varchar(30) NOT NULL COMMENT '优惠券模版code',
    `code`                 varchar(30) NOT NULL COMMENT '用户优惠券code',
    `user_id`              bigint(20) NOT NULL COMMENT '用户id',
    `mobile`               bigint(20) NOT NULL COMMENT '手机号',
    `status`               int2        NOT NULL COMMENT '优惠券状态：0-不可用 1-可用 2-已使用 3-已过期',
    `begin_time`           datetime    NOT NULL COMMENT '优惠券开始时间',
    `end_time`             datetime    NOT NULL COMMENT '优惠券结束时间',
    `create_time`          datetime    NOT NULL COMMENT '优惠券领取时间',
    `use_time`             datetime DEFAULT NULL COMMENT '优惠券使用时间',
    `update_time`          datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '用户优惠券表';

CREATE TABLE `t_coupon6`
(
    `id`                   bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `coupon_template_code` varchar(30) NOT NULL COMMENT '优惠券模版code',
    `code`                 varchar(30) NOT NULL COMMENT '用户优惠券code',
    `user_id`              bigint(20) NOT NULL COMMENT '用户id',
    `mobile`               bigint(20) NOT NULL COMMENT '手机号',
    `status`               int2        NOT NULL COMMENT '优惠券状态：0-不可用 1-可用 2-已使用 3-已过期',
    `begin_time`           datetime    NOT NULL COMMENT '优惠券开始时间',
    `end_time`             datetime    NOT NULL COMMENT '优惠券结束时间',
    `create_time`          datetime    NOT NULL COMMENT '优惠券领取时间',
    `use_time`             datetime DEFAULT NULL COMMENT '优惠券使用时间',
    `update_time`          datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '用户优惠券表';

CREATE TABLE `t_coupon7`
(
    `id`                   bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `coupon_template_code` varchar(30) NOT NULL COMMENT '优惠券模版code',
    `code`                 varchar(30) NOT NULL COMMENT '用户优惠券code',
    `user_id`              bigint(20) NOT NULL COMMENT '用户id',
    `mobile`               bigint(20) NOT NULL COMMENT '手机号',
    `status`               int2        NOT NULL COMMENT '优惠券状态：0-不可用 1-可用 2-已使用 3-已过期',
    `begin_time`           datetime    NOT NULL COMMENT '优惠券开始时间',
    `end_time`             datetime    NOT NULL COMMENT '优惠券结束时间',
    `create_time`          datetime    NOT NULL COMMENT '优惠券领取时间',
    `use_time`             datetime DEFAULT NULL COMMENT '优惠券使用时间',
    `update_time`          datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '用户优惠券表';

CREATE TABLE `t_coupon_activity`
(
    `id`                   bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `name`                 varchar(20) NOT NULL COMMENT '活动名称',
    `coupon_template_code` varchar(30) NOT NULL COMMENT '优惠券模版code',
    `total_number`         int8        NOT NULL COMMENT '券总数量 -1不限制',
    `limit_number`         int8        NOT NULL COMMENT '每人可领取数量',
    `status`               int2        NOT NULL COMMENT '活动状态：0-不可用 1-可用',
    `begin_time`           datetime    NOT NULL COMMENT '活动开始时间',
    `end_time`             datetime    NOT NULL COMMENT '活动结束时间',
    `create_time`          datetime    NOT NULL COMMENT '创建时间',
    `update_time`          datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '优惠券活动表';


CREATE TABLE `t_coupon_activity_log`
(
    `id`                 bigint(20) AUTO_INCREMENT NOT NULL COMMENT 'id',
    `coupon_activity_id` bigint(20) NOT NULL COMMENT '券活动id',
    `code`               varchar(30) NOT NULL COMMENT '用户优惠券code',
    `user_id`            bigint(20) NOT NULL COMMENT '用户id',
    `mobile`             bigint(20) NOT NULL COMMENT '手机号',
    `create_time`        datetime    NOT NULL COMMENT '创建时间',
    `update_time`        datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment '优惠券活动参与记录表';




