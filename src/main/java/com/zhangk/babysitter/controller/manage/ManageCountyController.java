package com.zhangk.babysitter.controller.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.service.common.CountyService;
import com.zhangk.babysitter.viewmodel.CountyView;

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

}
