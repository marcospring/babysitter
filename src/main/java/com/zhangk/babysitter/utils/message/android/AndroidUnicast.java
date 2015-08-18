package com.zhangk.babysitter.utils.message.android;

import com.zhangk.babysitter.utils.message.AndroidNotification;

public class AndroidUnicast extends AndroidNotification {
	public AndroidUnicast() {
		try {
			this.setPredefinedKeyValue("type", "unicast");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}