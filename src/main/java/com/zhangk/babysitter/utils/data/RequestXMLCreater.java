package com.zhangk.babysitter.utils.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.zhangk.babysitter.utils.common.Constants;

public class RequestXMLCreater {
	private RequestModule requestMoudle;
	private static RequestXMLCreater instance;
	private static final String SIGN_TYPE = "MD5";

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
		content.append("key=").append(appkey);
		String mySign = Md5Utils.hash(content.toString()).toUpperCase();
		return mySign;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getXmlMapResult(String xml) {
		Map<String, String> result = new HashMap<String, String>();
		requestMoudle = new RequestModule(xml);
		Element root = requestMoudle.getRoot();
		for (Iterator<Element> iterator = root.elementIterator(); iterator
				.hasNext();) {
			Element e = iterator.next();
			result.put(e.getName(), e.getTextTrim());
		}
		return result;
	}

	public Map<String, String> getPayRequestMap(String nonceStr, String body,
			String outTradeNo, String totalFee, String spbillCreateIp,
			String openid) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appid", Constants.WECHAT_OPENID_APPID);
		paramsMap.put("mch_id", Constants.WECHAT_OPENID_FRONTNO);// 商户号
		paramsMap.put("nonce_str", nonceStr);// 随机字符串，选为订单的guid
		paramsMap.put("body", body);
		paramsMap.put("out_trade_no", outTradeNo);//
		paramsMap.put("total_fee", totalFee);
		paramsMap.put("spbill_create_ip", spbillCreateIp);
		paramsMap.put("notify_url", Constants.WECHAT_OPENID_NOTIFY_URL);
		paramsMap.put("trade_type", Constants.WECHAT_OPENID_TRADE_TYPE);// JSAPI代表公众号支付
		paramsMap.put("openid", openid);//
		return paramsMap;
	}

	public Map<String, String> getPayResponseMap(String prePayid,
			String nonceStr) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appId", Constants.WECHAT_OPENID_APPID);
		paramsMap.put("nonceStr", nonceStr);// 随机字符串，选为订单的guid
		paramsMap.put("timeStamp", String.valueOf(new Date().getTime()));
		paramsMap.put("package", "prepay_id=" + prePayid);//
		paramsMap.put("signType", SIGN_TYPE);
		String paySign = createSign(paramsMap,
				Constants.WECHAT_OPENID_PAY_APPSECRET);
		paramsMap.put("paySign", paySign);
		return paramsMap;
	}
}

class RequestModule {
	private Document doc = null;
	private Element root = null;

	public RequestModule() {
		if (doc == null)
			doc = DocumentHelper.createDocument();
		if (root == null)
			root = doc.addElement("xml");
	}

	public RequestModule(String xml) {
		try {
			doc = DocumentHelper.parseText(xml);
			root = doc.getRootElement();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNodeValue(String key) {
		Element element = root.element(key);
		return element.getTextTrim();
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