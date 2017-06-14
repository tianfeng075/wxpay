package com.weixin;

import com.weixin.entity.WXConfigNode;

public class WXConfig {
	private static final int WXCONFIG = 1;
	
	
	private static WXConfigNode wxconfig;
	
	public static boolean init() {
		boolean result = false;
		try {
			wxconfig = Dao.getWXConfig().selectByPrimaryKey(WXCONFIG);
			if (wxconfig==null)
				return false;
			
			result = true;
		} catch (Exception e) {
			result = false;
		}
		
		return result;
	}

	public static WXConfigNode getWxconfig() {
		return wxconfig;
	}
	
	
}
