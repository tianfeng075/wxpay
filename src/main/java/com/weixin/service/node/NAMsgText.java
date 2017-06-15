package com.weixin.service.node;

import com.util.ComUtil;

public class NAMsgText extends NMsgBase{
	private String MsgId;
	private String Content;
	public NAMsgText() {
		super(true);
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
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
