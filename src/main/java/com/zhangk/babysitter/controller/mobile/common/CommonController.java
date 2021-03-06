package com.zhangk.babysitter.controller.mobile.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.CheckCode;
import com.zhangk.babysitter.entity.County;
import com.zhangk.babysitter.entity.FeedBack;
import com.zhangk.babysitter.entity.Image;
import com.zhangk.babysitter.service.common.CheckCodeService;
import com.zhangk.babysitter.service.common.CountyService;
import com.zhangk.babysitter.service.common.CredentialService;
import com.zhangk.babysitter.service.common.FeedBackService;
import com.zhangk.babysitter.service.common.ImageService;
import com.zhangk.babysitter.service.level.CountyLevelService;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CountyLevelView;
import com.zhangk.babysitter.viewmodel.CountyView;
import com.zhangk.babysitter.viewmodel.DicCredentialView;

@Controller("mobileCommonController")
@RequestMapping("/mobile/common")
public class CommonController extends BaseController {

	@Autowired
	private CountyService countyService;
	@Autowired
	private ImageService imgService;
	@Autowired
	private CountyLevelService levelService;
	@Autowired
	private FeedBackService feedbackService;
	@Autowired
	private CheckCodeService codeService;
	@Autowired
	private CredentialService credentialService;

	@ResponseBody
	@RequestMapping("/countyList")
	public PageResult getCountyList() {
		List<County> list = countyService.getCountyList();
		List<CountyView> listView = new ArrayList<CountyView>();
		for (County county : list) {
			listView.add(county.view());
		}
		res.put("result", listView);
		return res;
	}

	@ResponseBody
	@RequestMapping("/headImageList")
	public PageResult getHeadImageList(String countyGuid) {
		List<Image> list = imgService.imageList(countyGuid);
		res.put("result", list);
		return res;
	}

	@ResponseBody
	@RequestMapping("/levelList")
	public PageResult getCountyLevel(String countyGuid) {
		if (StringUtils.isEmpty(countyGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		List<CountyLevelView> list = levelService.countyLevels(countyGuid);
		res.put("result", list);
		return res;
	}

	@ResponseBody
	@RequestMapping("/levelListXY")
	public PageResult getCountyLevelXY(String x, String y) {
		if (StringUtils.isEmpty(x) || StringUtils.isEmpty(y))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult pres = levelService.countyLevelsXY(x, y, getResult());
		return pres;
	}

	@ResponseBody
	@RequestMapping("/addFeedBack")
	public PageResult addFeedBack(String msg, String telephone) {
		if (StringUtils.isEmpty(msg) || StringUtils.isEmpty(telephone))
			return getErrRes(ResultInfo.INF_EMPTY);
		FeedBack feedback = FeedBack.getInstance();
		feedback.setMessage(msg);
		feedback.setTelephone(telephone);
		feedbackService.addFeedBack(feedback);
		return res;
	}

	@ResponseBody
	@RequestMapping("/code")
	public PageResult getCode(String telephone, int type) {
		if (StringUtils.isEmpty(telephone) || type == 0)
			return getErrRes(ResultInfo.INF_EMPTY);
		telephone = telephone.replace(" ", "");
		CheckCode code = codeService.addCheckCode(telephone.trim(), type);
		res.setResult(ResultInfo.SUCCESS);
		res.put("result", code.view());
		return res;
	}

	@ResponseBody
	@RequestMapping("/credentials")
	public PageResult getCredentials() {
		List<DicCredentialView> list = credentialService.getCredentials();
		res.put("result", list);
		return res;
	}
}
