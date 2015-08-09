package com.zhangk.babysitter.controller.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;

@Controller
public class ErrorController extends BaseController {

	@ResponseBody
	@RequestMapping("/error")
	public PageResult errorPage() {
		return getErrRes(500, "系统异常");
	}
}
