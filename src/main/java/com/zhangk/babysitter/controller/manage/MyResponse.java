package com.zhangk.babysitter.controller.manage;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MyResponse {

	private static int DEFAULT_STATUS = 0;

	private static String DEFAULT_SUCCESS_MESSAGE = "success";

	public static ResponseEntity<Map<String, Object>> successResponse() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", DEFAULT_STATUS);
		resultMap.put("message", DEFAULT_SUCCESS_MESSAGE);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	public static ResponseEntity<Map<String, Object>> successResponse(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", DEFAULT_STATUS);
		resultMap.put("message", DEFAULT_SUCCESS_MESSAGE);
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	public static ResponseEntity<Map<String, Object>> successResponse(String message, Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", DEFAULT_STATUS);
		resultMap.put("message", message);
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	public static ResponseEntity<Map<String, Object>> successResponse(int status, String message, Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", status);
		resultMap.put("message", message);
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	public static ResponseEntity<Map<String, Object>> errorResponse(int status, String message) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", status);
		resultMap.put("message", message);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

}
