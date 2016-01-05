package com.zhangk.babysitter.utils.common;

public enum ResultInfo {

	INF_EMPTY(1000, "信息不全"), CHECK_CODE_ERROR(1001, "验证码错误"), BAD_REQUEST(500,
			"系统错误"), BABYSITTER_NULL(1002, "月嫂不存在"), BABYSITTER_ORDER_NULL(
			1003, "月嫂订单不存在"), VALID_USER_PASS(1004, "用户名或密码错误"), EMPLOYER_NULL(
			1005, "雇主不存在"), SERVICE_ORDER_NULL(1006, "雇主订单不存在"), RECOMMEND_NULL(
			1007, "推荐信息不存在"), COUNTY_NULL(1008, "城市不存在"), DATE_FORMAT_ERROR(
			1009, "日期格式化错误"), PROMOTION_NULL(1010, "活动信息不存在"), EXIST_USER(1011,
			"用户已存在"), NOTICE_NULL(1012, "通知不存在"), FILE_NULL(1013, "上传文件为空"), USER_NOT_NULL(
			1014, "用户已存在"), COUNTYLEVEL_NOT_NULL(1015, "级别已经存在"), BABYSITTER_NOT_NULL(
			1016, "月嫂已存在"), CREDENTIAL_NULL(1017, "证件不存在"), ADDRESS_EXCEPTION(
			1018, "定位异常"), COMPANY_NOT_NULL(1019, "公司不为空"), COMPANY_NULL(1020,
			"公司为空"), MANAGER_NOT_NULL(1021, "客户经理不为空"), MANAGER_NULL(1022,
			"客户经理为空"), SERVICE_ORDER_OVER(1023, "抢单已结束"), NONE_CUSTOMERMANAGER_SERVICE(
			1024, "没有安排客户经理，请拨打总台电话"), NO_BABYSITTER_SERVICE_ORDER(1025,
			"该订单还未添加月嫂订单"), IDENTIFICATION_NO_INVALID(1026, "身份证号不合法"), PAY_FAIL(
			1027, "支付失败"), MULTI_EMPLYER(1028, "openid对应多个雇主"), COUNTY_LEVEL_NULL(
			1029, "城市级别为空"), INVALID_ORDER(1030, "异常订单"), PREFERENTIAL_TYPE_NULL(
			1031, "优惠券类型不存在"), PROMOTER_NULL(1032, "优惠券推广人不存在"), PROMOTER_NOT_NULL(
			1033, "已经领取该优惠券"), BABYSITTER_TELEPHONE_NULL(1034, "月嫂电话为空"), EMPLOYEE_TELEPHONE_NULL(
			1035, "雇主电话为空"), SUCCESS(0, "操作成功");

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
