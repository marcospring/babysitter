package com.zhangk.babysitter.utils.common;

import java.util.UUID;

public class GUIDCreator {
	public static String GUID() {
		return formatUUID(UUID.randomUUID());
	}

	private static String formatUUID(UUID uuid) {
		String uuidStr = uuid.toString();
		uuidStr = uuidStr.replace("-", "").toUpperCase();
		return uuidStr;
	}
}
