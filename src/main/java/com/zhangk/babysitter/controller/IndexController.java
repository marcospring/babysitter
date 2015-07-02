package com.zhangk.babysitter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.entity.Babysister;

@Controller
@RequestMapping("/index")
public class IndexController {
	Logger log = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = "/index")
	public @ResponseBody Babysister index() {
		String aaa = "11111asdf";
		Babysister a = new Babysister();
		a.setId(1);
		a.setMobilePhone("15210799886");
		a.setPassword("123");
		a.setUsername("zhangsan");
		log.info(">>>>>>>>>>>>>>>>{}", aaa);
		return a;
	}
}
