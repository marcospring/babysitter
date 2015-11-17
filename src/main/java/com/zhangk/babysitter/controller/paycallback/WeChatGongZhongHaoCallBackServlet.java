package com.zhangk.babysitter.controller.paycallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.Md5Utils;

/**
 * Servlet implementation class WeChatGongZhongHaoCallBackServlet
 */
public class WeChatGongZhongHaoCallBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ENCODING = "UTF-8";
	private Logger logger = LoggerFactory
			.getLogger(WeChatGongZhongHaoCallBackServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WeChatGongZhongHaoCallBackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("<----------------------------------------------------------------------------------------->");
		System.out
				.println("<----------------------------------------------------------------------------------------->");
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(ENCODING);
		}
		response.setCharacterEncoding(ENCODING);
		PrintWriter out = response.getWriter();
		String xml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[ERROR]]></return_msg></xml>";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					request.getInputStream()));
			StringBuffer result = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			String params = result.toString();
			logger.info("wechat request params:" + params);
			Document doc = DocumentHelper.parseText(params);
			Element root = doc.getRootElement();
			if (!"SUCCESS".equalsIgnoreCase(root.elementText("return_code"))) {
				throw new RuntimeException("return_code error");
			}
			if (!"SUCCESS".equalsIgnoreCase(root.elementText("result_code"))) {
				throw new RuntimeException("result_code error");
			}
			String sign = root.elementText("sign");
			Iterator<Element> it = root.elementIterator();
			List<String> list = new ArrayList<String>();
			while (it.hasNext()) {
				Element element = it.next();
				String name = element.getName();
				if ("sign".equalsIgnoreCase(name)) {
					continue;
				}
				list.add(name);
				Collections.sort(list);
			}
			StringBuffer content = new StringBuffer();
			for (String str : list) {
				content.append(str).append("=")
						.append(root.element(str).getText()).append("&");
			}

			String mySign = Md5Utils.hash(
					content.append(
							"key=" + Constants.WECHAT_OPENID_PAY_APPSECRET)
							.toString()).toUpperCase();
			if (!mySign.equals(sign)) {
				throw new RuntimeException("sign verify error");
			}
			if (StringUtils.isEmpty(root.elementText("cash_fee_type"))) {
				if (!"CNY".equalsIgnoreCase(root.elementText("fee_type"))) {
					throw new RuntimeException("cash_fee_type error , not CNY");
				}
			}
			System.out
					.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>回调成功啦！！！！！！<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>回调成功啦！！！！！！<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			// boolean ret = SDKUtil.requestBilling(
			// root.elementText("out_trade_no"),
			// root.elementText("transaction_id"),
			// AmountUtils.changeF2Y(root.elementText("total_fee")),
			// ChannelPayWayMapper.WECHAT, true,
			// String.valueOf(ChannelEnum.CYSDK.value()));
			// if (ret) {
			// out.print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
			// return;
			// } else {
			// throw new RuntimeException("request billing error,return false");
			// }
		} catch (Exception e) {
			out.print(xml);
			logger.error("wechat pay callback error", e);
			e.printStackTrace();
			return;
		} finally {
			out.flush();
			out.close();
		}
		System.out
				.println("<----------------------------------------------------------------------------------------->");
		System.out
				.println("<----------------------------------------------------------------------------------------->");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
