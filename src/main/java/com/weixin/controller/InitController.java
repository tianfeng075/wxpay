package com.weixin.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weixin.Dao;
import com.weixin.WXConfig;
import com.weixin.WeixinClient;
import com.weixin.WeixinService;
import com.weixin.dao.WXConfigDao;

@Component
public class InitController {
	private static final Logger logger = LoggerFactory.getLogger(InitController.class);

	@Autowired
    private WXConfigDao daoWXConfig;
	
	@PostConstruct
	public void init() {
		//初始化数据库
		initDB();
		//初始化配置
		if (!WXConfig.init()) {
			logger.error("init WXConfig error");
			return;
		}
		
		//初始化微信服务对象
		if (!WeixinService.init()) {
			logger.error("init WeixinService error");
			return;
		}
		
		//初始化微信客户对象
		if (!WeixinClient.init()) {
			logger.error("init WeixinClient error");
			return;
		}
	}
	
	/**
	 * 初始化数据库对象
	 */
	private void initDB() {
		Dao.setWXConfig(daoWXConfig);
	}
}
