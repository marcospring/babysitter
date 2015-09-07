package com.zhangk.babysitter.controller.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.exception.BadRequestException;
import com.zhangk.babysitter.service.exployer.ServiceOrderService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.utils.httpclient.HttpHelper;
import com.zhangk.babysitter.utils.httpclient.ResponseContent;

@Controller
@RequestMapping("/wechat")
public class WeChatEmployerController extends BaseController {
	@Autowired
	private ServiceOrderService orderService;

	// @RequestMapping("/make")
	// public String make() {
	// return "wechat/make";
	// }
	//
	// @RequestMapping("/add")
	// public String add(HttpServletRequest request, String date, String price)
	// {
	// request.setAttribute("date", date);
	// request.setAttribute("price", price);
	// return "wechat/add";
	// }
	//
	// @RequestMapping("/makeOrder")
	// public String addpost(HttpServletRequest request, String add, String
	// ress,
	// String date, String price) {
	// try {
	// add = new String(
	// request.getParameter("add").getBytes("ISO-8859-1"), "utf-8");
	// ress = new String(request.getParameter("ress").getBytes(
	// "ISO-8859-1"), "utf-8");
	//
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// request.setAttribute("add", add);
	// request.setAttribute("ress", ress);
	// request.setAttribute("date", date);
	// request.setAttribute("price", price);
	// return "wechat/make";
	// }

	@ResponseBody
	@RequestMapping("/addOrder")
	public PageResult addServiceOrder(String date, String price, String address, String name, String mobile, String checkCode, String openid, String countyGuid) {
		if (StringUtils.isEmpty(date) || StringUtils.isEmpty(price) || StringUtils.isEmpty(checkCode) || StringUtils.isEmpty(address) || StringUtils.isEmpty(name)
				|| StringUtils.isEmpty(mobile) || StringUtils.isEmpty(openid)) {
			return getResult(ResultInfo.INF_EMPTY);
		}
		PageResult result = orderService.wechatAddServiceOrder(date, price, countyGuid, address, name, mobile, checkCode, openid, getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/orderList")
	public PageResult orderList(HttpServletRequest request, String phone, String openid) {
		if (StringUtils.isEmpty(phone) && StringUtils.isEmpty(openid)) {
			return getResult(ResultInfo.INF_EMPTY);
		}
		PageResult result = orderService.orderList(phone, openid, getResult());

		return result;
	}

	@ResponseBody
	@RequestMapping("/panicBabysitters")
	public PageResult getPanicBuyingBabysitters() {
		return null;
	}

	@ResponseBody
	@RequestMapping("/markBabysitter")
	public PageResult markBabysitter(String babysitterGuid, String orderGuid) {
		if (StringUtils.isEmpty(babysitterGuid) || StringUtils.isEmpty(orderGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = orderService.markBabysitter(babysitterGuid, orderGuid, getResult());
		return result;
	}

	@RequestMapping("/openid")
	public void getOpenid(String code, HttpServletResponse response) {
		if (StringUtils.isEmpty(code))
			throw new BadRequestException("");
		try {
			StringBuffer url = new StringBuffer();
			url.append(Constants.WECHAT_OPENID_URL).append("?");
			url.append("appid=").append(Constants.WECHAT_OPENID_URL).append("&");
			url.append("secret=").append(Constants.WECHAT_OPENID_APPSECRET).append("&");
			url.append("code=").append(code).append("&");
			url.append("grant_type=").append("authorization_code");
			ResponseContent ret = HttpHelper.getUrlRespContent(url.toString());
			response.getWriter().print(ret.getContent());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
