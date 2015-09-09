package com.zhangk.babysitter.controller.manage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.service.common.CountyService;
import com.zhangk.babysitter.viewmodel.CountyView;
import com.zhangk.babysitter.viewmodel.manage.ManageCountyView;

@Controller
@RequestMapping("/manage/county")
public class ManageCountyController {

	@Autowired
	private CountyService countyService;

	@ResponseBody
	@RequestMapping("/comboList")
	public Object countyList() {
		List<CountyView> counties = countyService.getCountyViewList();
		CountyView c = new CountyView();
		c.setId(0);
		c.setName("　");
		counties.add(0, c);
		return counties;
	}

	@ResponseBody
	@RequestMapping("/manageComboList")
	public Object comboList() {
		List<County> counties = countyService.getCountyList();
		List<ManageCountyView> views = new ArrayList<ManageCountyView>();
		for (County county : counties) {
			views.add(new ManageCountyView(county));
		}
		return views;
	}

	@RequestMapping("/go")
	public String go() {
		return "/manage/county/county";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list() {
		List<County> counties = countyService.getCountyList();
		List<ManageCountyView> views = new ArrayList<ManageCountyView>();
		for (County county : counties) {
			views.add(new ManageCountyView(county));
		}
		return views;
	}

	@RequestMapping("/goAdd")
	public Object goAdd() {
		return "manage/county/countyEdit";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(String name, String shortName, String pid) {
		County county = County.getInstance();
		county.setName(name);
		county.setShortName(shortName);
		if (StringUtils.isEmpty(pid))
			pid = "0";
		countyService.addCounty(county, Long.valueOf(pid));
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, long id) {
		request.setAttribute("vo", countyService.getCounty(id));
		return "manage/county/countyEdit";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object update(String id, String name, String shortName, String pid) {
		countyService.updateCounty(id, name, shortName, pid);
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		countyService.deleteCounty(id);
		return MyResponse.successResponse();
	}
}
