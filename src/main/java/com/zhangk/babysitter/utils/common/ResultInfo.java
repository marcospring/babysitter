package com.zhangk.babysitter.utils.common;

public enum ResultInfo {

	INF_EMPTY(1000, "信息不全"), CHECK_CODE_ERROR(1001, "验证码错误"), BAD_REQUEST(500,
			"系统错误"), BABYSITTER_NULL(1002, "月嫂不存在"), BABYSITTER_ORDER_NULL(
			1003, "月嫂订单不存在"), VALID_USER_PASS(1004, "用户名或密码错误"), EMPLOYER_NULL(
			1005, "雇主不存在"), SERVICE_ORDER_NULL(1006, "雇主订单不存在"), RECOMMEND_NULL(
			1007, "推荐信息不存在"), COUNTY_NULL(1007, "城市不存在"), DATE_FORMAT_ERROR(
			1008, "日期格式化错误"), PROMOTION_NULL(1009, "活动信息不存在"), EXIST_USER(1010,
			"用户已存在"), FILE_NULL(1011, "上传文件为空"), SUCCESS(0, "操作成功");

	private int code;
	private String msg;

	ResultInfo(int code, String msg) {
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