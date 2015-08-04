package com.zhangk.babysitter.utils.common;

public class Constants {
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_MANAGER = "sessionManager";
	// 新发布
	public static final int NEW_ORDER = 1;
	// 已付定金
	public static final int EARNEST_MONEY = 2;
	// 已付余款
	public static final int FULL_MONEY = 3;
	// 上户中
	public static final int IN_ORDER = 4;
	// 下户，待评价
	public static final int OUT_ORDER = 5;
	// 订单结束
	public static final int ORDER_OVER = 6;
	// 审核是否通过
	public static final int PASS = 1;
	public static final int NO_PASS = 0;
	// 是否已读
	public static final int READ = 1;
	public static final int NON_READ = 0;
	// 图片主域
	public static final String IMG_DOMAIN = PropertiesUtils.getString(
			"domain.image", "http://www.babysitter.com/babysitter");
	// 头像
	public static final String URL_HEAD = PropertiesUtils.getString("url.head",
			"/resource/user/head/");
	// 生活照
	public static final String URL_LIFE = PropertiesUtils.getString("url.life",
			"/resource/user/life/");
	// 证件
	public static final String URL_CARD = PropertiesUtils.getString("url.card",
			"/resource/user/card/");

	public static final int ORDER_ID = 1;
	public static final int BABYSITTER_ID = 2;
}
