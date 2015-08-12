package com.zhangk.babysitter.controller.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
