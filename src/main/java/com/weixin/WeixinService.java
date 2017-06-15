package com.weixin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.ComUtil;
import com.util.XmlUtil;
import com.weixin.entity.WXConfigNode;
import com.weixin.service.node.NAMsgEvent;
import com.weixin.service.node.NAMsgText;
import com.weixin.service.node.NMsgBase;
import com.weixin.service.node.NRMsgText;

public class WeixinService {
	private static final Logger logger = LoggerFactory.getLogger(WeixinService.class);  
	
    // 与接口配置信息中的Token要一致
    private static String token;
    
    public static boolean init() {
    	WXConfigNode config = WXConfig.getWxconfig();
		if (config==null)
			return false;
		token = config.getApptoken();
		return true;
    }

    /**
     * 验证签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { token, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
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

	public static String dealMsg(String strAccept) {
		
		NMsgBase base = toMsgBase(strAccept);
		if (base==null)
			return "fail";
		
		String strRs = null;
		try {
			switch (base.getMsgType()) {
			case WeixinConst.MSGTYPE_TEXT:
				strRs = TextMsg(strAccept);
				break;
			case WeixinConst.MSGTYPE_EVENT:
				strRs = EventMsg(strAccept);
				break;
			default:
				strRs = null;
				break;
			}
		} catch (InstantiationException | IllegalAccessException e) {
			strRs = null;
			logger.debug(e.toString());
		} finally {
			try{
				if (strRs==null)
					return ErrorMsg(base.getFromUserName(),base.getToUserName());
			} catch (InstantiationException | IllegalAccessException e) {
				logger.debug(e.toString());
			}
		}
		
		return strRs;	   
	}

	/**
	 * 解析文本
	 * @param strAccept
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static String TextMsg(String strAccept) throws InstantiationException, IllegalAccessException {
		NAMsgText acceptNode = ComUtil.fromXML(strAccept,NAMsgText.class);
		acceptNode.fromCD();
   		
   		logger.info(""+acceptNode.getMsgType()+"-"+acceptNode.getContent());
   		int CreateTime = ComUtil.getCurrentTime();
		String toMsgType = WeixinConst.MSGTYPE_TEXT;
		String toContent = "艾腾物联网:"+acceptNode.getContent();

		NRMsgText receiveNode = new NRMsgText(acceptNode.getFromUserName(),acceptNode.getToUserName(),CreateTime,toMsgType,toContent);
		receiveNode.toCD();
		return ComUtil.toXML(receiveNode,NRMsgText.class);
	}
	
	/**
	 * 解析事件
	 * @param strAccept
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static String EventMsg(String strAccept) throws InstantiationException, IllegalAccessException {
		NAMsgEvent acceptNode = ComUtil.fromXML(strAccept,NAMsgEvent.class);
		acceptNode.fromCD();
   		
   		logger.info(""+acceptNode.getMsgType()+"-"+acceptNode.getEvent()+"-"+acceptNode.getEventKey());
   		int CreateTime = ComUtil.getCurrentTime();
		String toMsgType = WeixinConst.MSGTYPE_TEXT;
		String toContent = "艾腾物联网:"+acceptNode.getEventKey();

		NRMsgText receiveNode = new NRMsgText(acceptNode.getFromUserName(),acceptNode.getToUserName(),CreateTime,toMsgType,toContent);
		receiveNode.toCD();
		return ComUtil.toXML(receiveNode,NRMsgText.class);
	}
	
	/**
	 * 出错时的默认回复
	 * @param base
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static String ErrorMsg(String toUserName,String FromUserName) throws InstantiationException, IllegalAccessException {
		int CreateTime = ComUtil.getCurrentTime();
		String MsgType = WeixinConst.MSGTYPE_TEXT;
		String Content = ComUtil.toCDATA("出错");
		NRMsgText receiveNode = new NRMsgText(toUserName,FromUserName,CreateTime,MsgType,Content);
		receiveNode.toCD();
		return ComUtil.toXML(receiveNode,NRMsgText.class);
	}
   
	/**
	 * 解析公共头信息
	 * @param str
	 * @return
	 */
	private static NMsgBase toMsgBase(String str) {
    	Map<String,Object> mapAccept = XmlUtil.xml2Map(str);
		if (mapAccept==null)
			return null;
		
		String ToUserName = (String)mapAccept.get("ToUserName");
		if (ToUserName==null)
			return null;
		
		String FromUserName = (String)mapAccept.get("FromUserName");
		if (FromUserName==null)
			return null;
		
		String strCreateTime = (String)mapAccept.get("CreateTime");
		if (strCreateTime==null)
			return null;
		int createTime = Integer.parseInt(strCreateTime);
		
		String MsgType = (String)mapAccept.get("MsgType");
		if (MsgType==null)
			return null;
		
		NMsgBase base = new NMsgBase(true,ToUserName,FromUserName,createTime,MsgType);
		if (!base.fromCD())
			return null;
		return base;
    }
}
