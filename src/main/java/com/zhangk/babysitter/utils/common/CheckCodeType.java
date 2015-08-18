package com.zhangk.babysitter.utils.common;

public enum CheckCodeType {

	REGISTER(1, "注册"), PUBLISH_ORDER(2, "发布订单"), LOGIN(3, "登陆"), CHANGE_PASS(4,
			"修改密码");

	private int code;
	private String msg;

	CheckCodeType(int code, String msg) {
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

	public static String getMsg(int type) {
		String msg = "";
		switch (type) {
		case 1:
			msg = REGISTER.getMsg();
			break;
		case 2:
			msg = PUBLISH_ORDER.getMsg();
			break;
		case 3:
			msg = LOGIN.getMsg();
			break;
		case 4:
			msg = CHANGE_PASS.getMsg();
			break;
		}
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
