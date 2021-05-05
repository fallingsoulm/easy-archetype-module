CREATE TABLE `cc_config` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `group` varchar(255) DEFAULT NULL COMMENT '分组',
  `config_key` varchar(255) DEFAULT NULL COMMENT '配置的key',
  `config_value` varchar(255) DEFAULT NULL COMMENT '配置的value值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;