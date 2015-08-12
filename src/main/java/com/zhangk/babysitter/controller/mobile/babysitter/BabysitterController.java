package com.zhangk.babysitter.controller.mobile.babysitter;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.CompanyNotice;
import com.zhangk.babysitter.entity.RecommendInfo;
import com.zhangk.babysitter.entity.RestInfo;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.common.NoticeService;
import com.zhangk.babysitter.service.common.PromotionService;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterView;
import com.zhangk.babysitter.viewmodel.CompanyNoticeView;
import com.zhangk.babysitter.viewmodel.PromotionView;
import com.zhangk.babysitter.viewmodel.RecommendInfoView;

@Controller("mobileBabysitterController")
@RequestMapping("/mobile/babysitter")
public class BabysitterController extends BaseController {

	@Autowired
	private BabysitterService babysitterService;
	@Autowired
	private PromotionService proService;
	@Autowired
	private NoticeService noticeService;

	@ResponseBody
	@RequestMapping("/babysitterList")

	public PageResult areaBabysitters(String countyGuid, Pagination<BabysitterView> page, String name, String orderStr) {
		// if (StringUtils.isEmpty(countyGuid))
		// return getErrRes(ResultInfo.INF_EMPTY);
		// page = babysitterService.getMobileBabysitters(countyGuid, page, name,
		// orderStr);
		// PageResult pres = getResult(ResultInfo.SUCCESS);
		// pres.put("result", page);
		return res;
	}

	@ResponseBody
	@RequestMapping("/babysitter")
	public PageResult babysitter(String guid) {
		if (StringUtils.isEmpty(guid))
			return getErrRes(ResultInfo.INF_EMPTY);
		BabysitterView view = babysitterService.getBabysitterView(guid);
		PageResult pres = getResult(ResultInfo.SUCCESS);
		pres.put("result", view);
		
		return pres;
	}

	@ResponseBody
	@RequestMapping("/recommend")
	public PageResult getRecommond(String countyGuid) {
		if (StringUtils.isEmpty(countyGuid))
			return getErrRes(ResultInfo.INF_EMPTY);

		if (info == null)
			return getErrRes(ResultInfo.RECOMMEND_NULL);
		RecommendInfoView view = info.view();

		PageResult pres = getResult(ResultInfo.SUCCESS);
		pres.put("result", view);
		
		return pres;
	}

	@ResponseBody
	@RequestMapping("/register")
				|| StringUtils.isEmpty(identificationNo)
				|| StringUtils.isEmpty(countyGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		// Pattern reg15 = Pattern
		// .compile("^[1-9]\\d{7}((0\\[1-9])|(1[0-2]))(([0\\[1-9]|1\\d|2\\d])|3[0-1])\\d{2}([0-9]|x|X){1}$");
		// Pattern reg18 = Pattern
		// .compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\[1-9]))|((1[0-2]))(([0\\[1-9]|1\\d|2\\d])|3[0-1])\\d{3}([0-9]|x|X){1}$");

		return result;
	}

	@ResponseBody
	@RequestMapping("/login")
	public PageResult login(String telephone, String password) {
		if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password))
			return getErrRes(ResultInfo.INF_EMPTY);

		return result;
	}

	@ResponseBody
	@RequestMapping("/changepass")

			return getErrRes(ResultInfo.INF_EMPTY);

		return result;
	}

	@ResponseBody
	@RequestMapping("/credentials")
	public PageResult credentials(String guid) {
		if (StringUtils.isEmpty(guid))
			return getErrRes(ResultInfo.INF_EMPTY);
		Babysitter babysitter = babysitterService.getBabysitter(guid);
		PageResult pres = getResult(ResultInfo.SUCCESS);
		pres.put("result", babysitter.view().getCredentials());
		
		return pres;
	}

	@ResponseBody
	@RequestMapping("/addRest")
				|| StringUtils.isEmpty(endDate))
			return getErrRes(ResultInfo.INF_EMPTY);
		Date beginDated = null;
		Date endDated = null;
		try {
			beginDated = ExpectedDateCreate.parseDate(beginDate);
			endDated = ExpectedDateCreate.parseDate(endDate);

		} catch (Exception e) {
			res.put("code", ResultInfo.DATE_FORMAT_ERROR.getCode());
			res.put("msg", ResultInfo.DATE_FORMAT_ERROR.getMsg());
			return res;
		}

		if (info == null)
			return getResult(ResultInfo.BABYSITTER_NULL);

		return res;
	}

	@ResponseBody
	@RequestMapping("/promotionList")
	public PageResult promotionList() {
		List<PromotionView> list = proService.getAllPromotion();
		res.put("result", list);
		return res;
	}

	@ResponseBody
	@RequestMapping("/addLowerSalary")
	public PageResult addLowerSalary(String guid, String money) {
		if (StringUtils.isEmpty(guid) || StringUtils.isEmpty(money))
			return getErrRes(ResultInfo.INF_EMPTY);
		res = babysitterService.addLowerSalary(guid, money, getResult());
		return res;
	}

	@ResponseBody
	@RequestMapping("/joinPromotion")
	public PageResult joinPromotion(String guid, String promotionGuid) {
		if (StringUtils.isEmpty(guid) || StringUtils.isEmpty(promotionGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		res = babysitterService.joinPromotion(guid, promotionGuid, getResult());
		return res;
	}

	@ResponseBody
	@RequestMapping("/notice")

		if (StringUtils.isEmpty(guid))
			return getResult(ResultInfo.INF_EMPTY);

		res.put("result", pa);
		return res;
	}

	@ResponseBody
	@RequestMapping("/readNotice")
	public PageResult readNotice(String guid) {
		if (StringUtils.isEmpty(guid))
			return getErrRes();
		return noticeService.readedNotice(guid, getResult());
	}

	@ResponseBody
	@RequestMapping("/nonReadCount")
	public PageResult nonReadNoticeCount(String guid) {
		if (StringUtils.isEmpty(guid))
			return getErrRes(ResultInfo.INF_EMPTY);
		return noticeService.getNONReadNoticeCount(guid, getResult());
	}

	@ResponseBody
	@RequestMapping("/updateBankCard")
				|| StringUtils.isEmpty(bankUserName)) {
			return getErrRes(ResultInfo.INF_EMPTY);
		}
				bankUserName, getResult());
		return res;
	}

	@ResponseBody
	@RequestMapping("/addOrder")

				|| StringUtils.isEmpty(telephone))
			return getErrRes(ResultInfo.INF_EMPTY);

				address, employerName, telephone, res);	}

	@ResponseBody
	@RequestMapping("/orderSort")
	public PageResult orderSort() {
		return babysitterService.orderScore(getResult());
	}

	@ResponseBody
	@RequestMapping("/scoreSort")
	public PageResult scoreSort() {
		return babysitterService.scoreSort(getResult());
	}
}
