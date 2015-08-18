package com.zhangk.babysitter.utils.message.ios;

import com.zhangk.babysitter.utils.message.IOSNotification;

public class IOSGroupcast extends IOSNotification {
	public IOSGroupcast() {
		try {
			this.setPredefinedKeyValue("type", "groupcast");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
