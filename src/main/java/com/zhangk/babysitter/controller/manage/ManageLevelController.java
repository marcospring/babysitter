package com.zhangk.babysitter.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Level;
import com.zhangk.babysitter.service.level.LevelService;

@Controller
@RequestMapping("/manage/level")
public class ManageLevelController extends BaseController {

	@Autowired
	private LevelService levelService;

	@ResponseBody
	@RequestMapping("/list")
	public Object list() {
		List<Level> levels = levelService.LevelList();
		return levels;
	}

	@RequestMapping("/go")
	public String go() {
		return "/manage/level/level";
	}

	@RequestMapping("/goAdd")
	public Object goAdd() {
		return "manage/level/levelEdit";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(String name, String score, String money) {
		Level level = Level.getInstance();
		level.setName(name);
		level.setScore(StringUtils.isEmpty(score) ? 0 : Long.valueOf(score));
		level.setMoney(StringUtils.isEmpty(money) ? 0 : Long.valueOf(money));
		levelService.addLevel(level);
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, long id) {
		request.setAttribute("vo", levelService.getLevel(id));
		return "manage/level/levelEdit";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object update(String id, String name, String score, String money) {
		levelService.updateLevel(id, name, score, money);
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		levelService.deleteLevel(id);
		return MyResponse.successResponse();
	}
}
