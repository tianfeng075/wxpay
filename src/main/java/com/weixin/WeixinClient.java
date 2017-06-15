package com.weixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.ComUtil;
import com.util.HttpsWeixin;
import com.util.XmlUtil;
import com.weixin.client.node.WeixinAccessTokenNode;
import com.weixin.client.node.WeixinCallbackIPNode;
import com.weixin.client.node.WeixinMenuNode;
import com.weixin.entity.WXConfigNode;

public class WeixinClient {
	private static final Logger logger = LoggerFactory.getLogger(WeixinClient.class);  
	
	private static String appID;
	private static String appsecret;
	
	
	private static String accesstoken;
	private static int expirestime;	//产生token时的系统时间（秒）
	private static int expiresin;	//token的有效时间
	
	public static boolean init() {
		WXConfigNode config = WXConfig.getWxconfig();
		if (config==null)
			return false;
		
		appID = config.getAppid();
		appsecret = config.getAppsecret();
		accesstoken = config.getAccesstoken();
		expirestime = config.getAccesssystime();
		expiresin = config.getAccessintime();
		
		return true;
	}
	
	private static boolean checkToken() {
		int curtime = ComUtil.getCurrentTime();
		if (expirestime+expiresin<curtime) {
			WeixinAccessTokenNode tokenNode = HttpsWeixin.getAccessToken(appID,appsecret);
			if (tokenNode==null)
				return false;
			expirestime = curtime;
			accesstoken = tokenNode.getAccess_token();
			expiresin = tokenNode.getExpires_in();
			
			WXConfigNode config = WXConfig.getWxconfig();
			if (config==null)
				return false;
			config.setAccesstoken(accesstoken);
			config.setAccesssystime(expirestime);
			config.setAccessintime(expiresin);
			config.setUpdatetime(ComUtil.getCurrentTime());
			
			if (Dao.getWXConfig().updateByPrimaryKey(config)!=1) {
				logger.error("update token to db error");
			}
		}
		return true;
	}
	
	public static String getWXIP() {
		if (!checkToken())
			return null;
		WeixinCallbackIPNode result = HttpsWeixin.getWXIP(accesstoken);
		if (result==null)
			return null;
		
		return XmlUtil.toJson(result);
	}
	
	public static boolean menuAdd(String data) {
		if (!checkToken())
			return false;
		return HttpsWeixin.menuAdd(accesstoken,data);
	}
	
	public static boolean menuDelete() {
		if (!checkToken())
			return false;
		return HttpsWeixin.menuDel(accesstoken);
	}
	
	public static String menuInfo() {
		if (!checkToken())
			return null;
		WeixinMenuNode info = HttpsWeixin.menuInfo(accesstoken);
		if (info==null)
			return null;
		String strRs = XmlUtil.toJson(info);
		return strRs;
	}
}
