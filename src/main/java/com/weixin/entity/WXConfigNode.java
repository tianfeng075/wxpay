package com.weixin.entity;

public class WXConfigNode {
	private int id;
	private String appid;
	private String appsecret;
	private String apptoken;
	private String accesstoken;
	private int accesssystime;
	private int accessintime;
	private long updatetime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getApptoken() {
		return apptoken;
	}
	public void setApptoken(String apptoken) {
		this.apptoken = apptoken;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public int getAccesssystime() {
		return accesssystime;
	}
	public void setAccesssystime(int accesssystime) {
		this.accesssystime = accesssystime;
	}
	public int getAccessintime() {
		return accessintime;
	}
	public void setAccessintime(int accessintime) {
		this.accessintime = accessintime;
	}
	public long getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(long updatetime) {
		this.updatetime = updatetime;
	}
}
