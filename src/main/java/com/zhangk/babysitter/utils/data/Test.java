package com.zhangk.babysitter.utils.data;

import com.zhangk.babysitter.utils.common.Md5Utils;

public class Test {
	public static void main(String[] args) {
		System.out
				.println(Md5Utils
						.hash("appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA&key=192006250b4c09247ec02edce69f6a2d"));
	}
}
