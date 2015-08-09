package com.zhangk.babysitter.controller.wechat;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.ServiceOrder;
import com.zhangk.babysitter.service.exployer.ServiceOrderService;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.ServiceOrderView;

@Controller
@RequestMapping("/wechat")
public class WeChatEmployerController extends BaseController {
	@Autowired
	private ServiceOrderService orderService;

	@RequestMapping("/make")
	public String make() {
		return "wechat/make";
	}

	@RequestMapping("/add")
	public String add(HttpServletRequest request, String date, String price) {
		request.setAttribute("date", date);
		request.setAttribute("price", price);
		return "wechat/add";
	}

	@RequestMapping("/makeOrder")
	public String addpost(HttpServletRequest request, String add, String ress,
			String date, String price) {
		try {
			add = new String(
					request.getParameter("add").getBytes("ISO-8859-1"), "utf-8");
			ress = new String(request.getParameter("ress").getBytes(
					"ISO-8859-1"), "utf-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("add", add);
		request.setAttribute("ress", ress);
		request.setAttribute("date", date);
		request.setAttribute("price", price);
		return "wechat/make";
	}

	@ResponseBody
	@RequestMapping("/addOrder")
	public PageResult addServiceOrder(String date, String price,
			String address, String name, String mobile, String checkCode) {
		if (StringUtils.isEmpty(date) || StringUtils.isEmpty(price)
				|| StringUtils.isEmpty(address) || StringUtils.isEmpty(name)
				|| StringUtils.isEmpty(mobile)) {
			return null;
		}
		ResultInfo result = orderService.addServiceOrder(date, price, "",
				address, name, mobile, checkCode);
		return getErrRes(result);
	}

	@RequestMapping("/orderList")
	public String orderList(HttpServletRequest request, String phone) {

		List<ServiceOrder> orders = orderService.orderList(phone.replace(" ",
				""));
		List<ServiceOrderView> view = new ArrayList<ServiceOrderView>();
		if (orders == null)
			orders = new ArrayList<ServiceOrder>();
		for (ServiceOrder order : orders) {
			view.add(order.view());
		}
		request.setAttribute("orders", view);
		return "wechat/order_end";
	}
}
