package com.zhangk.babysitter.utils.message.ios;

import com.zhangk.babysitter.utils.message.IOSNotification;

public class IOSBroadcast extends IOSNotification {
	public IOSBroadcast() {
		try {
			this.setPredefinedKeyValue("type", "broadcast");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
