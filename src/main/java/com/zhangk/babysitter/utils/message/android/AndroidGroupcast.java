package com.zhangk.babysitter.utils.message.android;

import com.zhangk.babysitter.utils.message.AndroidNotification;

public class AndroidGroupcast extends AndroidNotification {
	public AndroidGroupcast() {
		try {
			this.setPredefinedKeyValue("type", "groupcast");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
