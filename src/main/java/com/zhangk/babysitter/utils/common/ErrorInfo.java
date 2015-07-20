package com.zhangk.babysitter.utils.common;

public enum ErrorInfo {

	INF_EMPTY(1000, "信息不全");

	private int code;
	private String msg;

	ErrorInfo(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
