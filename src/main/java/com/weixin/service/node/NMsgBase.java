package com.weixin.service.node;

import com.util.ComUtil;

public class NMsgBase {
	protected boolean isCD;
	private String ToUserName;
	private String FromUserName;
	private int CreateTime;
	private String MsgType;
	public NMsgBase(boolean isCD) {
		ToUserName = null;
		FromUserName = null;
		CreateTime = 0;
		MsgType = null;
	}
	public NMsgBase(boolean isCD, String toUserName, String fromUserName, int createTime, String msgType) {
		super();
		this.isCD = isCD;
		ToUserName = toUserName;
		FromUserName = fromUserName;
		CreateTime = createTime;
		MsgType = msgType;
	}
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public int getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(int createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	
	public boolean toCD() {
		if (!isCD) {
			String tmpToUserName = ComUtil.toCDATA(ToUserName);
			String tmpFromUserName = ComUtil.toCDATA(FromUserName);
			String tmpMsgType = ComUtil.toCDATA(MsgType);
			if (tmpToUserName==null || tmpFromUserName==null || tmpMsgType==null)
				return false;
			ToUserName = tmpToUserName;
			FromUserName = tmpFromUserName;
			MsgType = tmpMsgType;
			isCD = true;
		}
		return true;
	}
	public boolean fromCD() {
		if (isCD) {
			String tmpToUserName = ComUtil.fromCDATA(ToUserName);
			String tmpFromUserName = ComUtil.fromCDATA(FromUserName);
			String tmpMsgType = ComUtil.fromCDATA(MsgType);
			if (tmpToUserName==null || tmpFromUserName==null || tmpMsgType==null)
				return false;
			ToUserName = tmpToUserName;
			FromUserName = tmpFromUserName;
			MsgType = tmpMsgType;
			isCD = false;
		}
		return true;
	}
}
