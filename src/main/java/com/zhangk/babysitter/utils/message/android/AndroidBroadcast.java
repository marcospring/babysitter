package com.zhangk.babysitter.utils.message.android;

import com.zhangk.babysitter.utils.message.AndroidNotification;

public class AndroidBroadcast extends AndroidNotification {
	public AndroidBroadcast() {
		try {
			this.setPredefinedKeyValue("type", "broadcast");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
