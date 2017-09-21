
create database exhibit;

use exhibit;

CREATE TABLE `env` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `env_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `host` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `port` int(10) DEFAULT NULL,
  PRIMARY KEY (`env_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='env'

CREATE TABLE `heartbeat` (
  `id` varchar(32) NOT NULL,
  `heartbeat_url` varchar(100) NOT NULL,
  `heartbeat_time` int(10) NOT NULL,
  `env` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

CREATE TABLE `provider` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `s_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务地址（相对）',
  `s_env` bigint(20) NOT NULL COMMENT '服务环境',
  `s_app_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务项目名',
  `s_interface` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务接口',
  `s_methods` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提供服务方法',
  `author` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提供人',
  `create_at` bigint(13) DEFAULT NULL,
  `update_at` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='提供者基础信息'

CREATE TABLE `provider_detail` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `provider_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `s_ip` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ip',
  `s_port` int(10) NOT NULL COMMENT '服务端口',
  `s_read_timeout` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接口超时时间',
  `s_desc` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `s_jvmId` int(10) DEFAULT NULL,
  `last_check_time` bigint(13) DEFAULT NULL COMMENT '最后检查时间',
  `destroy_time` bigint(13) DEFAULT NULL COMMENT '注销时间',
  `heartbeat_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_at` bigint(13) DEFAULT NULL,
  `status` int(3) NOT NULL COMMENT '101 正常',
  `update_at` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='实际提供者'


CREATE TABLE `consumers` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `provider_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `s_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务地址',
  `s_env` bigint(20) NOT NULL COMMENT '服务环境',
  `s_interface` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务接口',
  `c_ip` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消费服务ip',
  `c_app_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消费服务名',
  `status` int(3) DEFAULT NULL,
  `author` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消费人',
  `heartbeat_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_at` bigint(13) DEFAULT NULL,
  `destroy_time` bigint(13) DEFAULT NULL,
  `update_at` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消费者'

