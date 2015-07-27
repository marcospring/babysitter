package com.zhangk.babysitter.controller.mobile.babysitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.web.BaseController;
import com.zhangk.babysitter.service.babysitter.BabysitterOrderService;
import com.zhangk.babysitter.utils.common.ResultInfo;

@Controller
@RequestMapping("/mobile/order")
public class BabysitterOrderController extends BaseController {

	@Autowired
	private BabysitterOrderService orderService;

	@ResponseBody
	@RequestMapping("/addOrder")
	public PageResult addOrder(String babysitterGuid, String employerName,
			String countyGuid, String address, String name, String mobile,
			String date, String price) {
		if (StringUtils.isEmpty(babysitterGuid)
				|| StringUtils.isEmpty(employerName)
				|| StringUtils.isEmpty(countyGuid)
				|| StringUtils.isEmpty(address) || StringUtils.isEmpty(name)
				|| StringUtils.isEmpty(mobile) || StringUtils.isEmpty(date)
				|| StringUtils.isEmpty(price)) {
			return getErrRes(ResultInfo.INF_EMPTY);
		}
		ResultInfo result = orderService.addBabysitterOrder(babysitterGuid,
				countyGuid, address, name, mobile, date, price);
		return getResult(result);
	}
}
