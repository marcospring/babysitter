package com.zhangk.babysitter.controller.web.babysitter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.web.BaseController;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.entity.Level;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.level.LevelService;
import com.zhangk.babysitter.utils.common.Pagination;

@Controller
@RequestMapping("/babysitter")
public class BabysitterController extends BaseController {
	@Autowired
	private BabysitterService babysitterService;
	@Autowired
	private LevelService levelService;

	@RequestMapping("/babysitterList")
	public String babysitterList(HttpServletRequest request,
			HttpServletResponse response, Pagination<Babysitter> page) {
		page = babysitterService.getPageBabysitterList(page);
		request.setAttribute("page", page);
		page.setRequestUri(request.getRequestURI());
		List<Level> levels = levelService.LevelList();
		request.setAttribute("levels", levels);
		return "/babysitter/babysitterList";
	}

	@RequestMapping("/levelList")
	public String levelList() {
		List<Level> levels = levelService.LevelList();
		request.setAttribute("levels", levels);
		return "/babysitter/levelList";
	}

	@ResponseBody
	@RequestMapping("/addBabysitter")
	public PageResult addRole(String roleName) {
		if (StringUtils.isEmpty(roleName)) {
			return getErrRes(2, "信息不全！");
		}
		// Babysitter babysitter = Babysitter.getInstance();
		return res;
	}

	@ResponseBody
	@RequestMapping("/addLevel")
	public PageResult addLevel(String name, String score, String money) {
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(score)
				|| StringUtils.isEmpty(money)) {
			return getErrRes(2, "信息不全！");
		}
		Level level = Level.getInstance();
		level.setName(name);
		level.setScore(Long.parseLong(score));
		level.setMoney(Long.parseLong(money));
		levelService.addLevel(level);
		return res;
	}

	@RequestMapping("/orderList")
	public String babysitterOrderList(HttpServletRequest request,
			HttpServletResponse response, Pagination<BabysitterOrder> page,
			String name) {
		page = babysitterService.getPageOrderList(page, name);
		request.setAttribute("page", page);
		page.setRequestUri(request.getRequestURI());
		return "/babysitter/orderList";
	}

}
