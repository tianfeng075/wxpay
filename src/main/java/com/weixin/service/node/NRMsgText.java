package com.weixin.service.node;

import com.util.ComUtil;

public class NRMsgText extends NMsgBase{
	private String Content;
	public NRMsgText(String toUserName, String fromUserName, int createTime, String msgType, String content) {
		super(false,toUserName, fromUserName, createTime, msgType);
		Content = content;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	
	public boolean toCD() {
		if (!isCD) {
			String tmpContent = ComUtil.toCDATA(Content);
			if (tmpContent==null)
				return false;
			if (!super.toCD())
				return false;
			Content = tmpContent;
		}
		return true;
	}
	public boolean fromCD() {
		if (isCD) {
			String tmpContent = ComUtil.fromCDATA(Content);
			if (tmpContent==null)
				return false;
			if (!super.fromCD())
				return false;
			Content = tmpContent;
		}
		return true;
	}
}
