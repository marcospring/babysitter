package com.zhangk.babysitter.utils.data;

import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class RequestXMLCreater {
	private RequestModule requestMoudle;
	private static RequestXMLCreater instance;

	private RequestXMLCreater() {
	}

	public static RequestXMLCreater getInstance() {
		if (instance == null)
			instance = new RequestXMLCreater();
		return instance;
	}

	public String buildXmlString(Map<String, String> value, String appkey) {
		requestMoudle = new RequestModule();
		for (String key : value.keySet()) {
			requestMoudle.addRootNode(key, value.get(key));
		}
		String sign = createSign(value, appkey);
		requestMoudle.addRootNode("sign", sign);
		return requestMoudle.getDoc().asXML();
	}

	private String createSign(Map<String, String> value, String appkey) {
		Map<String, String> treeMap = new TreeMap<String, String>(value);
		StringBuffer content = new StringBuffer();
		for (String str : treeMap.keySet()) {
			content.append(str).append("=").append(value.get(str)).append("&");
		}
		String mySign = Md5Utils.hash(content.append("key=").append(appkey).toString()).toUpperCase();
		return mySign;
	}

}

class RequestModule {
	private Document doc = null;
	private Element root = null;

	public RequestModule() {
		doc = DocumentHelper.createDocument();
		root = doc.addElement("xml");
	}

	public void addRootNode(String key, String value) {
		Element keyElement = root.addElement(key);
		keyElement.addCDATA(value);
	}

	public Element getRoot() {
		return root;
	}

	public Document getDoc() {
		return doc;
	}

}