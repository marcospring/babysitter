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
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.service.exployer.ServiceOrderService;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.RemoteIPGeter;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.utils.httpclient.HttpHelper;
import com.zhangk.babysitter.utils.httpclient.ResponseContent;
import com.zhangk.babysitter.viewmodel.BabysitterView;

@Controller
@RequestMapping("/wechat")
public class WeChatEmployerController extends BaseController {
	@Autowired
	private ServiceOrderService orderService;
	@Autowired
	private EmployerService employerService;

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
	@RequestMapping("/search")
	public PageResult search(String pageSize, String pageNo, String countyGuid,
			String babysitterName, String orderFlag) {

		PageResult result = employerService.search(pageSize, pageNo,
				countyGuid, babysitterName, orderFlag, getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/addOrder")
	public PageResult addServiceOrder(String date, String countyLevelGuid,
			String address, String name, String mobile, String checkCode,
			String openid, String countyGuid, String isNotAdvice) {
		if (StringUtils.isEmpty(date) || StringUtils.isEmpty(countyLevelGuid)
				|| StringUtils.isEmpty(checkCode)
				|| StringUtils.isEmpty(address) || StringUtils.isEmpty(name)
				|| (StringUtils.isEmpty(mobile) && StringUtils.isEmpty(openid))) {
			return getResult(ResultInfo.INF_EMPTY);
		}
		PageResult result = orderService.wechatAddServiceOrder(date,
				countyLevelGuid, countyGuid, address, name, mobile, checkCode,
				openid, getResult(), isNotAdvice);
		return result;
	}

	@ResponseBody
	@RequestMapping("/orderList")
	public PageResult orderList(HttpServletRequest request, String phone,
			String openid) {
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
		if (StringUtils.isEmpty(babysitterGuid)
				|| StringUtils.isEmpty(orderGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = orderService.markBabysitter(babysitterGuid,
				orderGuid, getResult());
		return result;
	}

	// public PageResult

	@RequestMapping("/openid")
	public void getOpenid(String code, HttpServletResponse response) {
		if (StringUtils.isEmpty(code))
			throw new BadRequestException("");
		try {
			StringBuffer url = new StringBuffer();
			url.append(Constants.WECHAT_OPENID_URL).append("?");
			url.append("appid=").append(Constants.WECHAT_OPENID_APPID)
					.append("&");
			url.append("secret=").append(Constants.WECHAT_OPENID_APPSECRET)
					.append("&");
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

	@ResponseBody
	@RequestMapping("/babysitterList")
	public PageResult getEmployerRecommond(String date, String page,
			String countyGuid, String orderGuid) {
		Pagination<BabysitterView> result = employerService
				.getRecommendBabysitter(page, orderGuid);
		res.setResult(ResultInfo.SUCCESS);
		res.put("result", result);
		return res;
	}

	// @ResponseBody
	// @RequestMapping("/endService")
	// public PageResult endService(String orderGuid) {
	// if (StringUtils.isEmpty(orderGuid)) {
	// return getResult(ResultInfo.INF_EMPTY);
	// }
	// // ResultInfo result =
	// orderService.addBabysitterOrderEvaluate(employGuid,
	// // orderGuid, babysitterGuid, msg, score);
	// return getResult(result);
	// }

	@ResponseBody
	@RequestMapping("/addEvaluate")
	public PageResult addEvaluate(String orderGuid, String msg, String score) {
		if (StringUtils.isEmpty(orderGuid) || StringUtils.isEmpty(score)) {
			return getResult(ResultInfo.INF_EMPTY);
		}
		ResultInfo result = orderService.addBabysitterOrderEvaluate(orderGuid,
				msg, score);
		return getResult(result);
	}

	/**
	 * 添加选中月嫂订单，该选中月嫂为在首页月嫂推荐列表中或月嫂详细页面中点击预约调用该接口
	 *
	 * @param date
	 *            预产期
	 * @param price
	 *            订单价格
	 * @param address
	 *            雇主地址
	 * @param name
	 *            雇主姓名
	 * @param mobile
	 *            雇主电话
	 * @param checkCode
	 *            验证码
	 * @param openid
	 *            雇主openid
	 * @param countyGuid
	 *            雇主所属城市guid
	 * @param babysitterGuid
	 *            月嫂GUID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addMakeBabysitterOrder")
	public PageResult addMakeBabysitterOrder(String date, String address,
			String name, String mobile, String checkCode, String openid,
			String countyGuid, String babysitterGuid) {
		if (StringUtils.isEmpty(date) || StringUtils.isEmpty(address)
				|| StringUtils.isEmpty(name) || StringUtils.isEmpty(mobile)
				|| StringUtils.isEmpty(checkCode)
				|| StringUtils.isEmpty(countyGuid)
				|| StringUtils.isEmpty(babysitterGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = orderService.addMakeBabysitterOrder(date, address,
				name, mobile, checkCode, openid, countyGuid, babysitterGuid,
				getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/getBabysitterOrderInfo")
	public PageResult getBabysitterOrderInfo(String serviceOrderGuid) {
		if (StringUtils.isEmpty(serviceOrderGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = orderService.getBabysitterOrderInfo(
				serviceOrderGuid, getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/addWechatOrder")
	public PageResult payFrontMoney(HttpServletRequest request, String orderNo,
			String openid) {
		if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(openid)) {
			return getErrRes(ResultInfo.INF_EMPTY);
		}
		String ip = RemoteIPGeter.getIpAddr(request);
		PageResult result = orderService.payFrontMoney(orderNo, ip,
				getResult(), openid);

		return result;
	}

	@ResponseBody
	@RequestMapping("/serviceEnd")
	public PageResult serviceEnd(String orderGuid) {
		PageResult result = orderService.serviceEnd(orderGuid, getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/orderHistoryRecord")
	public PageResult orderHistoryRecord(String orderGuid) {
		PageResult result = orderService.orderHistoryRecord(orderGuid,
				getResult());
		return result;
	}
}
