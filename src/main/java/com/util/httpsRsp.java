package com.util;

public class httpsRsp {
	private int errcode;
    private String errmsg;
	public httpsRsp(int errcode, String errmsg) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
	}
	public int getErrcode() {
		return errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
