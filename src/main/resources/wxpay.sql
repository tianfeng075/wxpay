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

INSERT INTO `wx_config` (`id`, `appid`, `appsecret`, `apptoken`, `accesstoken`, `accesssystime`, `accessintime`, `updatetime`) VALUES ('1', 'wx9d9fa7e8979ecf7c', '8d55c714cd37a300354ff10a54b0c7ba', '96a4d427b2f399ebd34e16accd05ed68', 'e2Vq2lU--dyfwfNp4HpGrzud_sHnwWTzyYZ5pe45JSftHkjy3gfVRBlSb-VrU90CzCdcaCRe3Rk6mt5jqrownmFF1WHV-ZtrVhdc95EU7CQTs_67yyRsBYz5F78EjtTXMEPbACAUMX', '1497489063', '7200', '1497489064');
INSERT INTO `wx_config` (`id`, `appid`, `appsecret`, `apptoken`, `accesstoken`, `accesssystime`, `accessintime`, `updatetime`) VALUES ('2', 'wxb3a1aa09d69eb8a8', '6d1b840a0397e402248ae87087ed5a4c', 'dkjfieqw1234juuu', '6xP3BWd5YlJ-9iL6zcE5HHAnh9IthTXzxoKqNg4b0gNYSRVU2HJazIK-2Kz2R-gdHbxvcg6aimTWpgHz1-PbiVELWFPiPyaRMp0xGcCI3ZFAGls8Oov1b8fDIes_k7f_MVLeAFABKA', '1497432528', '7200', '1497432539');

