package com.weixin;

import com.weixin.dao.WXConfigDao;

public class Dao {
	private static WXConfigDao WXConfig;

	public static WXConfigDao getWXConfig() {
		return WXConfig;
	}

	public static void setWXConfig(WXConfigDao wXConfig) {
		WXConfig = wXConfig;
	}
}
