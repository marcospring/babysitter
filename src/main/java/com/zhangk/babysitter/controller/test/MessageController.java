package com.zhangk.babysitter.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.message.android.AndroidCustomizedcast;
import com.zhangk.babysitter.utils.message.ios.IOSCustomizedcast;

@Controller
@RequestMapping("/mobile/message")
public class MessageController extends BaseController {

	@ResponseBody
	@RequestMapping("/android")
	public PageResult androidMessage(String alias, String aliasType)
			throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast();
		customizedcast.setAppMasterSecret(Constants.ANDROID_APP_MASTER_SECRET);
		customizedcast.setPredefinedKeyValue("appkey",
				Constants.ANDROID_APP_KEY);
		customizedcast.setPredefinedKeyValue("timestamp",
				Integer.toString((int) (System.currentTimeMillis() / 1000)));
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setPredefinedKeyValue("alias", alias);
		// TODO Set your alias_type here
		customizedcast.setPredefinedKeyValue("alias_type", aliasType);
		customizedcast.setPredefinedKeyValue("ticker",
				"Android customizedcast ticker");
		customizedcast.setPredefinedKeyValue("title", "中文的title");
		customizedcast.setPredefinedKeyValue("text",
				"Android customizedcast text");
		customizedcast.setPredefinedKeyValue("after_open", "go_app");
		customizedcast.setPredefinedKeyValue("display_type", "notification");
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setPredefinedKeyValue("production_mode", "true");
		String result = customizedcast.send();
		res.put("msg", result);
		return res;
	}

	@ResponseBody
	@RequestMapping("/ios")
	public PageResult iosMessage(String alias, String aliasType)
			throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast();
		customizedcast.setAppMasterSecret(Constants.IOS_APP_MASTER_SECRET);
		customizedcast.setPredefinedKeyValue("appkey", Constants.IOS_APP_KEY);
		customizedcast.setPredefinedKeyValue("timestamp",
				Integer.toString((int) (System.currentTimeMillis() / 1000)));
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setPredefinedKeyValue("alias", alias);
		// TODO Set your alias_type here
		customizedcast.setPredefinedKeyValue("alias_type", aliasType);
		customizedcast.setPredefinedKeyValue("alert", "IOS 个性化测试");
		customizedcast.setPredefinedKeyValue("badge", 0);
		customizedcast.setPredefinedKeyValue("sound", "chime");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		customizedcast.setPredefinedKeyValue("production_mode", "false");
		String result = customizedcast.send();
		res.put("msg", result);
		return res;
	}

}
