package com.zhangk.babysitter.controller.website;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.CustomerManager;
import com.zhangk.babysitter.service.exployer.ServiceOrderService;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;
import com.zhangk.babysitter.utils.common.ResultInfo;

@Controller
@RequestMapping("website/order")
public class WebSiteOrderController extends BaseController {
	@Autowired
	private ServiceOrderService orderService;

	@ResponseBody
	@RequestMapping("/addOrder")
	public PageResult addOrder(String countyGuid, String date, String name,
			String mobile, String checkCode) {
		if (StringUtils.isEmpty(date) || StringUtils.isEmpty(checkCode)
				|| StringUtils.isEmpty(name) || StringUtils.isEmpty(mobile)) {
			return getResult(ResultInfo.INF_EMPTY);
		}
		PageResult result = orderService.wechatAddServiceOrder(date, "0",
				countyGuid, "", name, mobile, checkCode, "", getResult(), 0);
		if ("0".equals(result.get("code").toString())) {
			String number = ExpectedDateCreate.getChineseWeekdayNumber(Calendar
					.getInstance());
			CustomerManager manager = orderService.getCustomerManager(
					countyGuid, number);
			if (manager == null) {
				result.setResult(ResultInfo.NONE_CUSTOMERMANAGER_SERVICE);
				result.put("result", "");
			} else {
				result.put("result", manager.view());
			}
		}
		return result;
	}
}
