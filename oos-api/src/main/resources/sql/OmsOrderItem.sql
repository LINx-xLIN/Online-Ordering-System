CREATE TABLE `oms_order_item` (
	`id` VARCHAR(20) NOT NULL COLLATE 'utf8_bin',
	`item_id` VARCHAR(50) NOT NULL COMMENT '商品id' COLLATE 'utf8_bin',
	`order_id` VARCHAR(50) NOT NULL COMMENT '订单id' COLLATE 'utf8_bin',
	`num` INT(10) NULL DEFAULT NULL COMMENT '商品购买数量',
	`title` VARCHAR(200) NULL DEFAULT NULL COMMENT '商品标题' COLLATE 'utf8_bin',
	`price` Double NULL DEFAULT NULL COMMENT '商品单价',
	`total_fee` Double NULL DEFAULT NULL COMMENT '商品总金额',
	`pic_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '商品图片地址' COLLATE 'utf8_bin',
	PRIMARY KEY (`id`),
	INDEX `item_id` (`item_id`),
	INDEX `order_id` (`order_id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'oms_order_item';

