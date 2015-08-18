package com.zhangk.babysitter.utils.message.ios;

import com.zhangk.babysitter.utils.message.IOSNotification;

public class IOSUnicast extends IOSNotification {
	public IOSUnicast() {
		try {
			this.setPredefinedKeyValue("type", "unicast");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
