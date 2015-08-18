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

	public static final String CODE_DOMAIN = PropertiesUtils.getString(
			"code.domain", "sandboxapp.cloopen.com");
	public static final String CODE_PORT = PropertiesUtils.getString(
			"code.port", "8883");
	public static final String CODE_ACCOUNT = PropertiesUtils.getString(
			"code.account", "8a48b5514ecd7fa8014edcde010a15c1");
	public static final String CODE_AUTH = PropertiesUtils.getString(
			"code.auth", "b820805e71984068a021377b3822a798");
	public static final String CODE_APPID = PropertiesUtils.getString(
			"code.appid", "8a48b5514ecd7fa8014edcde557015c4");
	public static final String MSG_TEMPLATE = PropertiesUtils.getString(
			"code.template", "1");

	public static final int CREDENTIAL_TYPE_CREDENTIAL = 1;
	public static final int CREDENTIAL_TYPE_PROFESSIONAL = 2;

	// 相册guid
	public static final String LIFE_IMAGE_GUID = PropertiesUtils.getString(
			"guid.image", "001BA330344E41B794DCEB71666C0D39");
	// 银行卡guid
	public static final String BANK_CARD_GUID = PropertiesUtils.getString(
			"guid.bankcard", "FEB6822A1F404A95A7A99DD50A002FFE");

	public static final String MAP_URL = PropertiesUtils.getString("map.url",
			"http://api.map.baidu.com/geocoder");

}
