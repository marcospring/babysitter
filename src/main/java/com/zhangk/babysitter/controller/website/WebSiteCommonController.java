package com.zhangk.babysitter.controller.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.utils.common.ResultInfo;

@Controller
@RequestMapping("/website/common")
public class WebSiteCommonController extends BaseController {

	@Autowired
	private BabysitterService babysitterService;

	@ResponseBody
	@RequestMapping("/search")
	public PageResult search(String countyGuid, String expectedDate,
			String levelGuid) {
		if (StringUtils.isEmpty(countyGuid)
				|| StringUtils.isEmpty(expectedDate)
				|| StringUtils.isEmpty(levelGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = babysitterService.search(countyGuid, expectedDate,
				levelGuid, getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/NameSearch")
	public PageResult search(String name) {
		if (StringUtils.isEmpty(name))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = babysitterService.nameSearch(name, getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/indexRecommond")
	public PageResult recommondIndex(String countyGuid) {
		if (StringUtils.isEmpty(countyGuid))
			return getErrRes(ResultInfo.COUNTY_NULL);
		PageResult result = babysitterService.getNewBabysitterRecommend(
				countyGuid, getResult());

		return result;
	}

	@ResponseBody
	@RequestMapping("/recommondCount")
	public PageResult recommondCount(String countyGuid) {
		if (StringUtils.isEmpty(countyGuid))
			return getErrRes(ResultInfo.COUNTY_NULL);
		PageResult result = babysitterService.getRecommondCount(countyGuid,
				getResult());

		return result;
	}

}
