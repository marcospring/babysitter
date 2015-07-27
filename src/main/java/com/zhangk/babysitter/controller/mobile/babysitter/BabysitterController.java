package com.zhangk.babysitter.controller.mobile.babysitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.web.BaseController;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterView;

@Controller("mobileBabysitterController")
@RequestMapping("/mobile/babysitter")
public class BabysitterController extends BaseController {

	@Autowired
	private BabysitterService babysitterService;

	@ResponseBody
	@RequestMapping("/babysitterList")
	public PageResult areaBabysitters(String countyGuid,
			Pagination<BabysitterView> page, String name, String orderStr) {
		if (StringUtils.isEmpty(countyGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		page = babysitterService.getMobileBabysitters(countyGuid, page, name,
				orderStr);
		res.put("result", page);
		return res;
	}

	@ResponseBody
	@RequestMapping("/babysitter")
	public PageResult babysitter(String guid) {
		if (StringUtils.isEmpty(guid))
			return getErrRes(ResultInfo.INF_EMPTY);
		BabysitterView view = babysitterService.getBabysitterView(guid);
		res.put("result", view);
		return res;
	}
}
