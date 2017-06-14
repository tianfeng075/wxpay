package com.weixin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.ComUtil;
import com.util.WeixinMsg;
import com.weixin.entity.WXConfigNode;

public class WeixinService {
	private static final Logger logger = LoggerFactory.getLogger(WeixinService.class);  
	
    // 与接口配置信息中的Token要一致
    private static String token;// = "dkjfieqw1234juuu";
    
    public static boolean init() {
    	WXConfigNode config = WXConfig.getWxconfig();
		if (config==null)
			return false;
		token = config.getApptoken();
		return true;
    }

    /**
     * 验证签名
     * 
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { token, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        // Arrays.sort(arr);
        ComUtil.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = ComUtil.byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

	public static WeixinMsg dealMsg(WeixinMsg weixinMsg) {
		if (weixinMsg==null)
			return null;
		logger.info("receive msgtype="+weixinMsg.getMsgType()+",object="+weixinMsg.getContent());
		
		String toMsgType = "text";
		String toContent = "返回的消息:"+weixinMsg.getContent();
		return new WeixinMsg(toMsgType,toContent);
	}
   
}
