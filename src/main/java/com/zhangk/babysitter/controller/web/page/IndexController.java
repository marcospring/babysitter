package com.zhangk.babysitter.controller.web.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.entity.Babysitter;

@Controller
@RequestMapping("/index")
public class IndexController {
	Logger log = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = "/index")
	public @ResponseBody Babysitter index() {
		String aaa = "11111asdf";
		Babysitter a = new Babysitter();
		a.setId(1);
		a.setMobilePhone("15210799886");
		a.setPassword("123");
		a.setUsername("zhangsan");
		log.info(">>>>>>>>>>>>>>>>{}", aaa);
		return a;
	}
}
