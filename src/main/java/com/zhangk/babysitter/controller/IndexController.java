package com.zhangk.babysitter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {
	Logger log = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping("/index")
	public String index() {
		String aaa = "11111asdf";
		log.info(">>>>>>>>>>>>>>>>{}", aaa);
		return "a";
	}
}
