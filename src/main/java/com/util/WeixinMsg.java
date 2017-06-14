package com.util;

public class WeixinMsg {
	private String MsgType;
	private String Content;
	public WeixinMsg(String msgType, String content) {
		super();
		MsgType = msgType;
		Content = content;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
}
