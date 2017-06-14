package com.weixin.client.node;

import java.util.List;

public class WeixinCallbackIPNode {
	private List<String> ip_list;

	public List<String> getIp_list() {
		return ip_list;
	}

	public void setIp_list(List<String> ip_list) {
		this.ip_list = ip_list;
	}

	@Override
	public String toString() {
		return "WeixinCallbackIPNode [ip_list=" + ip_list + "]";
	}
}
