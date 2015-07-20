package com.zhangk.babysitter.controller.mobile.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.web.BaseController;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.Image;
import com.zhangk.babysitter.service.common.CountyService;
import com.zhangk.babysitter.service.common.ImageService;
import com.zhangk.babysitter.service.level.CountyLevelService;
import com.zhangk.babysitter.utils.common.ErrorInfo;
import com.zhangk.babysitter.viewmodel.CountyLevelView;

@Controller("mobileCommonController")
@RequestMapping("/mobile/common")
public class CommonController extends BaseController {

	@Autowired
	private CountyService countyService;
	@Autowired
	private ImageService imgService;
	@Autowired
	private CountyLevelService levelService;

	@ResponseBody
	@RequestMapping("/countyList")
	public PageResult getCountyList() {
		List<County> list = countyService.getCountyList();
		res.put("result", list);
		return res;
	}

	@ResponseBody
	@RequestMapping("/headImageList")
	public PageResult getHeadImageList() {
		List<Image> list = imgService.imageList();
		res.put("result", list);
		return res;
	}

	@ResponseBody
	@RequestMapping("/levelList")
	public PageResult getCountyLevel(String countyGuid) {
		if (StringUtils.isEmpty(countyGuid))
			return getErrRes(ErrorInfo.INF_EMPTY);
		List<CountyLevelView> list = levelService.countyLevels(countyGuid);
		res.put("result", list);
		return res;
	}
}
