package com.zhangk.babysitter.controller.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.common.CountyService;
import com.zhangk.babysitter.service.level.CountyLevelService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterView;
import com.zhangk.babysitter.viewmodel.CountyLevelView;
import com.zhangk.babysitter.viewmodel.CountyView;

@Controller
@RequestMapping("/manage/babysitter")
public class ManageBabysitterController extends BaseController {

	@Autowired
	private BabysitterService babysitterService;
	@Autowired
	private CountyService countyService;
	@Autowired
	private CountyLevelService levelService;

	@RequestMapping("/go")
	public String go() {
		return "/manage/babysitter/babysitter";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, int page, int rows, String countyId, String name, String levelid, String telephone, String cardNo) {
		Pagination<Babysitter> babysitters = new Pagination<Babysitter>(page, rows);
		Pagination<BabysitterView> views = babysitterService.getManageBabysitters(babysitters, countyId, name, levelid, telephone, cardNo);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}

	@RequestMapping("/goAdd")
	public Object goAdd(HttpServletRequest request) {

		return "manage/babysitter/babysitterEdit";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(String name, String password, String identificationNo, long lowerSalary, String mobilePhone, long countyId, long levelId, String birthday,
			String nativePlace, String introduce) {
		ResultInfo result = babysitterService.manageAddBabysitter(name, password, identificationNo, lowerSalary, mobilePhone, countyId, levelId, birthday, nativePlace, introduce);
		if (result == ResultInfo.BABYSITTER_NOT_NULL)
			return MyResponse.errorResponse(ResultInfo.BABYSITTER_NOT_NULL.getCode(), ResultInfo.BABYSITTER_NOT_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, long id) {
		Babysitter babysitter = babysitterService.getBabysitter(id);
		List<CountyView> counties = countyService.getCountyViewList();
		List<CountyLevelView> levels = levelService.countyLevels(babysitter.getLevel().getCounty().getGuid());
		request.setAttribute("counties", counties);
		request.setAttribute("levels", levels);
		return "manage/babysitter/babysitterEdit";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object update(String id, String name, String password, String identificationNo, long lowerSalary, String mobilePhone, long countyId, long levelId, String birthday,
			String nativePlace, String introduce) {
		ResultInfo result = babysitterService.manageUpdateBabysitter(id, name, password, identificationNo, lowerSalary, mobilePhone, countyId, levelId, birthday, nativePlace,
				introduce);
		if (result == ResultInfo.BABYSITTER_NULL)
			return MyResponse.errorResponse(ResultInfo.BABYSITTER_NULL.getCode(), ResultInfo.BABYSITTER_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(long id) {
		babysitterService.deleteBabysitter(id);
		return MyResponse.successResponse();
	}
}