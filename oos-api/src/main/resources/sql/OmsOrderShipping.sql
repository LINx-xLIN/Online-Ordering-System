CREATE TABLE `oms_order_shipping` (
	`order_id` VARCHAR(50) NOT NULL COMMENT '订单ID',
	`receiver_name` VARCHAR(20) NULL DEFAULT NULL COMMENT '收货人全名',
	`receiver_phone` VARCHAR(20) NULL DEFAULT NULL COMMENT '固定电话',
	`receiver_mobile` VARCHAR(30) NULL DEFAULT NULL COMMENT '移动电话',
	`receiver_state` VARCHAR(10) NULL DEFAULT NULL COMMENT '省份',
	`receiver_city` VARCHAR(10) NULL DEFAULT NULL COMMENT '城市',
	`receiver_district` VARCHAR(20) NULL DEFAULT NULL COMMENT '区/县',
	`receiver_address` VARCHAR(200) NULL DEFAULT NULL COMMENT '收货地址，如：xx路xx号',
	`receiver_zip` VARCHAR(6) NULL DEFAULT NULL COMMENT '邮政编码,如：310001',
	`created` DATETIME NULL DEFAULT NULL,
	`updated` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`order_id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'oms_order_shipping';
