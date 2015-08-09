package com.zhangk.babysitter.controller.mobile.employer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.service.exployer.ServiceOrderService;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterView;

@Controller("mobileEmployerController")
@RequestMapping("/mobile/employer")
public class EmployerController extends BaseController {
	@Autowired
	private ServiceOrderService orderService;
	@Autowired
	private EmployerService employerService;

	@ResponseBody
	@RequestMapping("/addOrder")
	public PageResult addServiceOrder(String date, String price,
			String countyGuid, String address, String name, String mobile,
			String checkCode) {
		if (StringUtils.isEmpty(date) || StringUtils.isEmpty(price)
				|| StringUtils.isEmpty(countyGuid)
				|| StringUtils.isEmpty(address) || StringUtils.isEmpty(name)
				|| StringUtils.isEmpty(mobile)) {
			return getResult(ResultInfo.INF_EMPTY);
		}
		ResultInfo result = orderService.addServiceOrder(date, price,
				countyGuid, address, name, mobile, checkCode);
		return getResult(result);
	}

	@ResponseBody
	@RequestMapping("/addEvaluate")
	public PageResult addEvaluate(String employGuid, String orderGuid,
			String babysitterGuid, String msg, String score) {
		if (StringUtils.isEmpty(employGuid) || StringUtils.isEmpty(orderGuid)
				|| StringUtils.isEmpty(babysitterGuid)
				|| StringUtils.isEmpty(msg) || StringUtils.isEmpty(score)) {
			return getResult(ResultInfo.INF_EMPTY);
		}
		ResultInfo result = orderService.addBabysitterOrderEvaluate(employGuid,
				orderGuid, babysitterGuid, msg, score);
		return getResult(result);
	}

	@ResponseBody
	@RequestMapping("/recommend")
	public PageResult getEmployerRecommond(String date, int page,
			String countyGuid) {
		List<BabysitterView> result = employerService.getRecommendBabysitter(
				date, page, countyGuid);
		res.put("result", result);
		res.put("page", page == 0 ? 1 : page);
		return res;
	}
}
