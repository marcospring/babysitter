package com.zhangk.babysitter.utils.common;

public class Constants {
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_MANAGER = "sessionManager";
	public static final String PAY_SUCCESS = "SUCCESS";
	public static final String PAY_MESSAGE_OK = "OK";
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
	// 是/否标志
	public static final int N = 1;
	public static final int Y = 0;

	// 微信排序类型
	public static final int WECHAT_ORDER_LEVEL = 1;
	// 客户满意度
	public static final int WECHAT_ORDER_SATISFACTION = 2;
	// 订单数量
	public static final int WECHAT_ORDER_ORDER_COUNT = 3;

	// 月嫂证件上传数量标志，0为可传多张，1为只能传一张
	public static final int CREDENTIAL_COUNT_FLAG_MULTI = 0;
	public static final int CREDENTIAL_COUNT_FLAG_SINGLE = 1;

	// 月嫂证件上传积分标志，0为可多次积分，1为只能积分一次
	public static final int CREDENTIAL_SCORE_FLAG_MULTI = 0;
	public static final int CREDENTIAL_SCORE_FLAG_SINGLE = 1;

	// 优惠行为使用方向
	public static final int SHARE = 1;
	public static final int USERS = 2;
	// 优惠卷使用情况
	public static final int USE = 1;// 使用
	public static final int UNUSE = 2;// 未使用

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
	// 二维码
	public static final String URL_BARCODE = PropertiesUtils.getString(
			"url.barcode", "/resource/user/barcode/");

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
			"code.template", "30935");
	public static final String MSG_TEMPLATE_ADVICE = PropertiesUtils.getString(
			"code.template.advice", "39248");
	public static final String MSG_TEMPLATE_BABYSITTER_INORDER_ADVICE = PropertiesUtils
			.getString("code.template.babysitter.inorder.advice", "59386");
	public static final String MSG_TEMPLATE_EMPLOYEE_ADVICE = PropertiesUtils
			.getString("code.template.employee.advice", "59385");
	public static final String MSG_TEMPLATE_ADVICE_PHONE = PropertiesUtils
			.getString("code.template.advice.phone", "13522556002");

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

	public static final String ANDROID_APP_MASTER_SECRET = PropertiesUtils
			.getString("message.mastersecret.android",
					"znnb2ob8jb1aajmrxt7t75hjyban3kfo");
	public static final String ANDROID_APP_KEY = PropertiesUtils.getString(
			"message.appkey.android", "55adee28e0f55a71800017a1");
	public static final String IOS_APP_MASTER_SECRET = PropertiesUtils
			.getString("message.mastersecret.ios",
					"ugkceklqmgyndcrk10skwte8au0dl6cg");
	public static final String IOS_APP_KEY = PropertiesUtils.getString(
			"message.appkey.ios", "55adeebce0f55a71a40014f8");
	// 加盟店
	public static final int COMPANY_CHAIN_STORE = 1;
	// 直营店
	public static final int COMPANY_REGULAR_CHAIN = 2;

	public static final String WECHAT_OPENID_URL = PropertiesUtils.getString(
			"wechat.url", "https://api.weixin.qq.com/sns/oauth2/access_token");;
	public static final String WECHAT_OPENID_APPID = PropertiesUtils.getString(
			"wechat.appid", "wxbaca4dbccd6548a7");;
	public static final String WECHAT_OPENID_APPSECRET = PropertiesUtils
			.getString("wechat.appsecret", "aa3cba812479f1a9c610301d4722bd99");
	public static final String WECHAT_OPENID_FRONTNO = PropertiesUtils
			.getString("wechat.frontno", "1253335901");
	public static final String WECHAT_OPENID_PAY_APPSECRET = PropertiesUtils
			.getString("wechat.pay.appsecret",
					"12aa12AA3212er4nkkasi7snajde8alm");
	public static final String WECHAT_OPENID_PAY_URL = PropertiesUtils
			.getString("wechat.pay.url",
					"https://api.mch.weixin.qq.com/pay/unifiedorder");
	public static final String WECHAT_OPENID_NOTIFY_URL = PropertiesUtils
			.getString("wechat.pay.notify.url",
					"http://mobile.yuesaostar.com/babysitter/callback/GongZhongHao");
	public static final String WECHAT_OPENID_TRADE_TYPE = PropertiesUtils
			.getString("wechat.pay.trade.type", "JSAPI");

	// 雇主订单是否已经选中月嫂
	public static final int MARK = 1;
	public static final int UNMARK = 0;

	// 雇主订单默认抢单数量
	public static final int SERVICE_ORDER_COUNT = PropertiesUtils.getInt(
			"serviceorder.count", 10);

	public static final String DEFAULT_HEAD_URL = "/dmg/head.png";

	public static final String SERVICEORDER_DEFAULT_RATE = PropertiesUtils
			.getString("countylevel.default.rate", "0.8");

	public static final String DEFAULT_QIANG_BABYSITTER_COUNT = PropertiesUtils
			.getString("default.qiangdan.babysitter.count", "7");

}
