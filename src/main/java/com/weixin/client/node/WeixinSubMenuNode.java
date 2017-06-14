package com.weixin.client.node;

import java.util.List;

public class WeixinSubMenuNode {
	private String name;
	private List<Object> sub_button;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Object> getSub_button() {
		return sub_button;
	}
	public void setSub_button(List<Object> sub_button) {
		this.sub_button = sub_button;
	}
}
