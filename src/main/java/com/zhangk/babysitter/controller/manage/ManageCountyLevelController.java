package com.zhangk.babysitter.controller.manage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.CountyLevel;
import com.zhangk.babysitter.service.level.CountyLevelService;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CountyLevelView;

@Controller
@RequestMapping("/manage/countyLevel")
public class ManageCountyLevelController extends BaseController {

	@Autowired
	private CountyLevelService levelService;

	@RequestMapping("/go")
	public String go() {
		return "/manage/countyLevel/countyLevel";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(String countyid) {
		if (StringUtils.isEmpty(countyid))
			countyid = "0";
		long countyIdl = Long.valueOf(countyid);
		List<CountyLevelView> levels = levelService.countyLevels(countyIdl);
		return levels;
	}

	@RequestMapping("/goAdd")
	public Object goAdd(HttpServletRequest request) {
		return "manage/countyLevel/countyLevelEdit";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(long countyid, long levelid, long score, long money) {
		ResultInfo result = levelService.addCountyLevel(countyid, levelid, score, money);
		if (result == ResultInfo.COUNTYLEVEL_NOT_NULL)
			return MyResponse.errorResponse(ResultInfo.COUNTYLEVEL_NOT_NULL.getCode(), ResultInfo.COUNTYLEVEL_NOT_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, long id) {
		CountyLevel level = levelService.getCountyLevel(id);
		request.setAttribute("vo", level);
		return "manage/countyLevel/countyLevelEdit";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object update(long id, long countyid, long levelid, long score, long money) {
		levelService.updateCountyLevel(id, countyid, levelid, score, money);
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(long id) {
		levelService.deleteCountyLevel(id);
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/comboList")
	public Object comboList(String countyid) {
		List<CountyLevelView> levels = null;
		if (StringUtils.isEmpty(countyid) || "0".equals(countyid)) {
			levels = new ArrayList<CountyLevelView>();
		} else {
			long countyIdl = Long.valueOf(countyid);
			levels = levelService.countyLevels(countyIdl);
		}

		return levels;
	}
}
