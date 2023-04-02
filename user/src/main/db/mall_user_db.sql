create
database IF NOT EXISTS `mall_user_db` default character set utf8mb4 collate utf8mb4_general_ci;

use
mall_user_db;

CREATE TABLE `t_user`
(
    `id`          bigint(20) NOT NULL COMMENT '用户id',
    `name`        varchar(20) DEFAULT NULL COMMENT '用户昵称',
    `mobile`      bigint(20) DEFAULT NULL COMMENT '手机号',
    `level`       int(1) DEFAULT NULL COMMENT '用户等级 0-游客，1-vip，2-店主',
    `tags`        varchar(50) DEFAULT NULL COMMENT '用户标签 10-新VIP，11-老VIP，12-未自购VIP、13-已自购VIP；20-新店主，21-老店主2，2-未自购店主，23-已自购店主，24-未开单店主，25-已开单店主',
    `create_time` datetime NOT NULL COMMENT '用户注册时间',
    `update_time` datetime NOT NULL COMMENT '用户更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_user0` (
                            `id` bigint(20) NOT NULL COMMENT '用户id',
                            `name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
                            `mobile` bigint(20) DEFAULT NULL COMMENT '手机号',
                            `level` int(1) DEFAULT NULL  COMMENT '用户等级 0-游客，1-vip，2-店主',
                            `tags` varchar(50) DEFAULT NULL COMMENT '用户标签 10-新VIP，11-老VIP，12-未自购VIP、13-已自购VIP；20-新店主，21-老店主2，2-未自购店主，23-已自购店主，24-未开单店主，25-已开单店主',
                            `create_time` datetime NOT NULL COMMENT '用户注册时间',
                            `update_time` datetime NOT NULL COMMENT '用户更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_user1` (
                            `id` bigint(20) NOT NULL COMMENT '用户id',
                            `name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
                            `mobile` bigint(20) DEFAULT NULL COMMENT '手机号',
                            `level` int(1) DEFAULT NULL  COMMENT '用户等级 0-游客，1-vip，2-店主',
                            `tags` varchar(50) DEFAULT NULL COMMENT '用户标签 10-新VIP，11-老VIP，12-未自购VIP、13-已自购VIP；20-新店主，21-老店主2，2-未自购店主，23-已自购店主，24-未开单店主，25-已开单店主',
                            `create_time` datetime NOT NULL COMMENT '用户注册时间',
                            `update_time` datetime NOT NULL COMMENT '用户更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_user2` (
                            `id` bigint(20) NOT NULL COMMENT '用户id',
                            `name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
                            `mobile` bigint(20) DEFAULT NULL COMMENT '手机号',
                            `level` int(1) DEFAULT NULL  COMMENT '用户等级 0-游客，1-vip，2-店主',
                            `tags` varchar(50) DEFAULT NULL COMMENT '用户标签 10-新VIP，11-老VIP，12-未自购VIP、13-已自购VIP；20-新店主，21-老店主2，2-未自购店主，23-已自购店主，24-未开单店主，25-已开单店主',
                            `create_time` datetime NOT NULL COMMENT '用户注册时间',
                            `update_time` datetime NOT NULL COMMENT '用户更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_user3` (
                            `id` bigint(20) NOT NULL COMMENT '用户id',
                            `name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
                            `mobile` bigint(20) DEFAULT NULL COMMENT '手机号',
                            `level` int(1) DEFAULT NULL  COMMENT '用户等级 0-游客，1-vip，2-店主',
                            `tags` varchar(50) DEFAULT NULL COMMENT '用户标签 10-新VIP，11-老VIP，12-未自购VIP、13-已自购VIP；20-新店主，21-老店主2，2-未自购店主，23-已自购店主，24-未开单店主，25-已开单店主',
                            `create_time` datetime NOT NULL COMMENT '用户注册时间',
                            `update_time` datetime NOT NULL COMMENT '用户更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_user4` (
                            `id` bigint(20) NOT NULL COMMENT '用户id',
                            `name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
                            `mobile` bigint(20) DEFAULT NULL COMMENT '手机号',
                            `level` int(1) DEFAULT NULL  COMMENT '用户等级 0-游客，1-vip，2-店主',
                            `tags` varchar(50) DEFAULT NULL COMMENT '用户标签 10-新VIP，11-老VIP，12-未自购VIP、13-已自购VIP；20-新店主，21-老店主2，2-未自购店主，23-已自购店主，24-未开单店主，25-已开单店主',
                            `create_time` datetime NOT NULL COMMENT '用户注册时间',
                            `update_time` datetime NOT NULL COMMENT '用户更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_user5` (
                            `id` bigint(20) NOT NULL COMMENT '用户id',
                            `name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
                            `mobile` bigint(20) DEFAULT NULL COMMENT '手机号',
                            `level` int(1) DEFAULT NULL  COMMENT '用户等级 0-游客，1-vip，2-店主',
                            `tags` varchar(50) DEFAULT NULL COMMENT '用户标签 10-新VIP，11-老VIP，12-未自购VIP、13-已自购VIP；20-新店主，21-老店主2，2-未自购店主，23-已自购店主，24-未开单店主，25-已开单店主',
                            `create_time` datetime NOT NULL COMMENT '用户注册时间',
                            `update_time` datetime NOT NULL COMMENT '用户更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_user6` (
                            `id` bigint(20) NOT NULL COMMENT '用户id',
                            `name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
                            `mobile` bigint(20) DEFAULT NULL COMMENT '手机号',
                            `level` int(1) DEFAULT NULL  COMMENT '用户等级 0-游客，1-vip，2-店主',
                            `tags` varchar(50) DEFAULT NULL COMMENT '用户标签 10-新VIP，11-老VIP，12-未自购VIP、13-已自购VIP；20-新店主，21-老店主2，2-未自购店主，23-已自购店主，24-未开单店主，25-已开单店主',
                            `create_time` datetime NOT NULL COMMENT '用户注册时间',
                            `update_time` datetime NOT NULL COMMENT '用户更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_user7` (
                            `id` bigint(20) NOT NULL COMMENT '用户id',
                            `name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
                            `mobile` bigint(20) DEFAULT NULL COMMENT '手机号',
                            `level` int(1) DEFAULT NULL  COMMENT '用户等级 0-游客，1-vip，2-店主',
                            `tags` varchar(50) DEFAULT NULL COMMENT '用户标签 10-新VIP，11-老VIP，12-未自购VIP、13-已自购VIP；20-新店主，21-老店主2，2-未自购店主，23-已自购店主，24-未开单店主，25-已开单店主',
                            `create_time` datetime NOT NULL COMMENT '用户注册时间',
                            `update_time` datetime NOT NULL COMMENT '用户更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
