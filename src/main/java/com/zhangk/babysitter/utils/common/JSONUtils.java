package com.zhangk.babysitter.utils.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONUtils {

	/**
	 * json串转换，null转为""
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
	}
}
