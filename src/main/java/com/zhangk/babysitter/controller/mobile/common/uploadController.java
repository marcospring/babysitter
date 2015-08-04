package com.zhangk.babysitter.controller.mobile.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.web.BaseController;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.utils.common.UploadFileUtils;

@Controller
@RequestMapping("/file")
public class uploadController extends BaseController {

	@Autowired
	private BabysitterService babysitterService;

	@ResponseBody
	@RequestMapping("/image/head")
	public PageResult uploadHead(HttpServletRequest request, String guid)
			throws IllegalStateException, IOException {

		UploadFileUtils fileUtil = UploadFileUtils.newInstance();
		fileUtil.setRequest(request);
		if (StringUtils.isEmpty(guid))
			return getErrRes(ResultInfo.INF_EMPTY);
		res = babysitterService.updateHeadImage(guid, request, res);
		return res;
	}

	@ResponseBody
	@RequestMapping("/image/life")
	public PageResult uploadLife(HttpServletRequest request, String guid)
			throws IllegalStateException, IOException {
		if (StringUtils.isEmpty(guid))
			return getErrRes(ResultInfo.INF_EMPTY);
		res = babysitterService.addLifeImage(guid, request, res);
		return res;
	}
}
