CREATE DATABASE IF NOT EXISTS wisdom DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS merchant_user (
	id BIGINT NOT NULL AUTO_INCREMENT,
	user_id VARCHAR (20) NOT NULL COMMENT '商户ID',
	user_name VARCHAR (20) NOT NULL COMMENT '用户名',
	pass_word VARCHAR (20) NOT NULL COMMENT '密码',
	email VARCHAR (50) NOT NULL COMMENT '邮箱',
	PRIMARY KEY (id),
	UNIQUE (user_name),
	INDEX (user_id)
) COMMENT '商户用户表';

CREATE TABLE merchandis (
	id BIGINT NOT NULL AUTO_INCREMENT,
	merchandis_id VARCHAR(40) NOT NULL COMMENT '商品id',
	merchandis_name VARCHAR(20) NOT NULL COMMENT '商品名称',
	price DECIMAL (12, 2) NOT NULL COMMENT '商品价格',
	category VARCHAR(20) NOT NULL COMMENT '商品类别',
	pnum INT(11) NOT NULL COMMENT '商品数量',
	img_wisdomurl VARCHAR(100) NOT NULL COMMENT '商品图片',
	description VARCHAR(255) NOT NULL COMMENT '商品描述',
	del_flag TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
	user_id VARCHAR(20) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE INDEX (merchandis_id),
	FOREIGN KEY (user_id) REFERENCES merchant_user(user_id)
)COMMENT '商品表';

/*DROP TABLE merchant;

CREATE TABLE IF NOT EXISTS merchant (
	id BIGINT NOT NULL AUTO_INCREMENT,
	merc_id VARCHAR (20) NOT NULL COMMENT '商户ID',
	merc_name VARCHAR (20) NOT NULL COMMENT '商户名称',
	merc_key VARCHAR (32) NOT NULL COMMENT '商户密钥',
	merc_safe_key VARCHAR (32) NOT NULL COMMENT '商户安全码',
	pay_rate DECIMAL (12, 4) NOT NULL COMMENT '支付费率',
	pay_rate2 DECIMAL (12, 4) NOT NULL COMMENT '支付费率2',
	refund_rate DECIMAL (12, 4) NOT NULL COMMENT '退款费率',
	charge_back_rate DECIMAL (12, 4) NOT NULL COMMENT '拒付费率',
	cash_rate DECIMAL (12, 4) NOT NULL COMMENT '提现费率',
	remit_rate DECIMAL (12, 4) NOT NULL COMMENT '代付费率',
	is_refund_allow TINYINT COMMENT '是否允许退款',
	legal_name VARCHAR (20) COMMENT '法人代表',
	phone VARCHAR (100) COMMENT '联系方式',
	mobile VARCHAR (20) COMMENT '手机',
	shop_url VARCHAR (200) COMMENT '商城URL',
	amount DECIMAL (12, 2) NOT NULL COMMENT '余额',
	proxy_amount DECIMAL (12, 2) NOT NULL COMMENT '代付余额',
	frozen_cash_amount DECIMAL (12, 2) NOT NULL COMMENT '提现冻结余额',
	frozen_proxy_amount DECIMAL (12, 2) NOT NULL COMMENT '代付冻结余额',
	tx_amount DECIMAL (12, 2) NOT NULL DEFAULT 0 COMMENT '累计交易金额',
	remit_amount DECIMAL (12, 2) NOT NULL DEFAULT 0 COMMENT '累计提现金额',
	bnk_notify_cnt BIGINT NOT NULL DEFAULT 0 COMMENT '银行通知次数',
	merc_notify_cnt BIGINT NOT NULL DEFAULT 0 COMMENT '商户通知次数',
	proxy_server_ip  VARCHAR (400)  COMMENT '代付IP限制',
	ip_limit VARCHAR (400) COMMENT 'IP限制',
	host VARCHAR (400) COMMENT '域名限制',
	del_flag TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
	create_time TIMESTAMP COMMENT '创建时间',
	last_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  COMMENT '最后更新时间',
	version_no INT COMMENT '版本号',
	PRIMARY KEY (id),
	UNIQUE (merc_id)
) COMMENT '商户属性表';



DROP TABLE merchant_user;

CREATE TABLE IF NOT EXISTS merchant_user (
	id BIGINT NOT NULL AUTO_INCREMENT,
	merc_id VARCHAR (20) NOT NULL COMMENT '商户ID',
	admin_type TINYINT NOT NULL COMMENT '管理员类型',
	`role_id` int(11) DEFAULT NULL COMMENT '管理员角色',
	lang VARCHAR (20) NOT NULL DEFAULT 'zh_CN' COMMENT '语言',
	email VARCHAR (50) NOT NULL COMMENT '邮箱',
	user_name VARCHAR (20) NOT NULL COMMENT '用户名',
	pass_word VARCHAR (32) NOT NULL COMMENT '密码',
	last_login_time TIMESTAMP NOT NULL COMMENT '最后登录时间',
	last_login_ip VARCHAR (20) NOT NULL COMMENT '最后登录IP',
	ip_limit VARCHAR (300)  COMMENT 'IP限制',
	del_flag TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
	create_time TIMESTAMP COMMENT '创建时间',
	last_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  COMMENT '最后更新时间',
	version_no INT COMMENT '版本号',
	PRIMARY KEY (id),
	UNIQUE (user_name),
	INDEX (merc_id)
) COMMENT '商户用户表';*/
