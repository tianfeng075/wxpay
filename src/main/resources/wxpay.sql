DROP TABLE IF EXISTS `wx_config`;
CREATE TABLE `wx_config` (  
  `id` int(11) NOT NULL COMMENT '主键id',  
  `appid` char(32) NOT NULL COMMENT '公众号openid',  
  `appsecret` varchar(255) NOT NULL COMMENT '公众号密钥',
  `apptoken` varchar(255) NOT NULL COMMENT '公众号token',  
  `accesstoken` varchar(255) NOT NULL COMMENT 'access token',
  `accesssystime` int(11) NOT NULL COMMENT '产生accesstoken时的系统时间',
  `accessintime` int(11) NOT NULL COMMENT 'accesstoken的有效时间',
  `updatetime` bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment='微信配置表';

insert  into 
	`wx_config`(`appid`,`appsecret`,`apptoken`,`accesstoken`,`accesssystime`,`accessintime`,`updatetime`)
values 
	('wxb3a1aa09d69eb8a8','6d1b840a0397e402248ae87087ed5a4c','dkjfieqw1234juuu','',0,0,0);
