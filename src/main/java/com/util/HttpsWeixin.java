package com.util;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.weixin.client.node.WeixinAccessTokenNode;
import com.weixin.client.node.WeixinCallbackIPNode;

public class HttpsWeixin {
	private static final Logger logger = Logger.getLogger(HttpsWeixin.class);
    
    /**
    * 获取微信access_token
    * @param appid
    * @param secret
    * @return access_token
    */
	public static WeixinAccessTokenNode getAccessToken(String appid,String secret) {
		if(StringUtils.isEmpty(appid)|| StringUtils.isEmpty(secret))
        {
        	logger.error("appid or secret is null");
            return null;
        }
		String token = null;
		try {
			String url = geturlAccessToken(appid,secret);
			httpsRsp rsp = ComUtil.httpsGetSend(url);
			if (rsp==null) {
				logger.error("access token return null");
			} else if (rsp.getErrcode()==200) {
				token = rsp.getErrmsg();
			} else {
				logger.info("access token code="+rsp.getErrcode()+",desc="+rsp.getErrmsg());
			}
		} catch (Exception e) {
			token = null;
			logger.error(e);
		}
		if (token==null)
			return null;
		
		if (!check(token))
			return null;

		return ComUtil.fromJson(token, WeixinAccessTokenNode.class);
	}
	
	/**
	    * 获取微信服务器IP
	    * @param appid
	    * @param secret
	    * @return access_token
	    */
		public static WeixinCallbackIPNode getCallbackIP(String accesstoken) {
			if(StringUtils.isEmpty(accesstoken))
	        {
	        	logger.error("accesstoken is null");
	            return null;
	        }
			String token = null;
			try {
				String url = geturlCallbackIP(accesstoken);
				httpsRsp rsp = ComUtil.httpsGetSend(url);
				if (rsp==null) {
					logger.error("access token return null");
				} else if (rsp.getErrcode()==200) {
					token = rsp.getErrmsg();
				} else {
					logger.info("access token code="+rsp.getErrcode()+",desc="+rsp.getErrmsg());
				}
			} catch (Exception e) {
				token = null;
				logger.error(e);
			}
			if (token==null)
				return null;
			
			if (!check(token))
				return null;

			return ComUtil.fromJson(token, WeixinCallbackIPNode.class);
		}
		
	public static boolean getMenuAdd(String accesstoken,String param) {
		if(StringUtils.isEmpty(accesstoken))
        {
        	logger.error("accesstoken is null");
            return false;
        }
		String token = null;
		try {
			String url = geturlMenuAdd(accesstoken);
			//token = HttpClientUtil.doPost(url,param,"utf-8");
			httpsRsp rsp = ComUtil.httpsPostSend(url,param);
			if (rsp==null) {
				logger.error("access token return null");
			} else if (rsp.getErrcode()==200) {
				token = rsp.getErrmsg();
			} else {
				logger.info("access token code="+rsp.getErrcode()+",desc="+rsp.getErrmsg());
			}
		} catch (Exception e) {
			token = null;
			logger.error(e);
		}
		if (token==null)
			return false;
		
		httpsRsp rsp = ComUtil.fromJson(token, httpsRsp.class);
		if (rsp==null || rsp.getErrcode()!=0)
			return false;
		return true;
	}
	
	public static boolean getMenuDelete(String accesstoken) {
		if(StringUtils.isEmpty(accesstoken))
        {
        	logger.error("accesstoken is null");
            return false;
        }
		String token = null;
		try {
			String url = geturlMenuDelete(accesstoken);
			httpsRsp rsp = ComUtil.httpsGetSend(url);
			if (rsp==null) {
				logger.error("access token return null");
			} else if (rsp.getErrcode()==200) {
				token = rsp.getErrmsg();
			} else {
				logger.info("access token code="+rsp.getErrcode()+",desc="+rsp.getErrmsg());
			}
		} catch (Exception e) {
			token = null;
			logger.error(e);
		}
		if (token==null)
			return false;
		
		httpsRsp rsp = ComUtil.fromJson(token, httpsRsp.class);
		if (rsp==null || rsp.getErrcode()!=0)
			return false;
		return true;		
	}
	
	private static boolean check(String str) {
		httpsRsp rsp = ComUtil.fromJson(str, httpsRsp.class);
		if (rsp!=null) {
			if (rsp.getErrcode()!=0 || rsp.getErrmsg()!=null) {
				logger.info("errcode:"+rsp.getErrcode()+",errmsg:"+rsp.getErrmsg());
				return false;
			}
		}
		return true;
	}
	
	private static String geturlAccessToken(String appid,String secret) {
		return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
	}
	private static String geturlCallbackIP(String accesstoken) {
		return "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="+accesstoken;
	}
	private static String geturlMenuAdd(String accesstoken) {
		return "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accesstoken;
	}
	private static String geturlMenuDelete(String accesstoken) {
		return "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+accesstoken;
	}
}
