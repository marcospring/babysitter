package com.zhangk.babysitter.controller.mobile.babysitter;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.web.BaseController;
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
		if (StringUtils.isEmpty(countyGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		page = babysitterService.getMobileBabysitters(countyGuid, page, name, orderStr);
		res.put("result", page);
		return res;
	}

	@ResponseBody
	@RequestMapping("/babysitter")
	public PageResult babysitter(String guid) {
		if (StringUtils.isEmpty(guid))
			return getErrRes(ResultInfo.INF_EMPTY);
		BabysitterView view = babysitterService.getBabysitterView(guid);
		res.put("result", view);
		return res;
	}

	@ResponseBody
	@RequestMapping("/recommend")
	public PageResult getRecommond(String countyGuid) {
		if (StringUtils.isEmpty(countyGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		RecommendInfo info = babysitterService.getNewBabysitterRecommend(countyGuid);
		if (info == null)
			return getErrRes(ResultInfo.RECOMMEND_NULL);
		RecommendInfoView view = info.view();
		view.setBabysitterCount(babysitterService.getBabysitterCountByCounty(countyGuid));
		res.put("result", view);
		return res;
	}

	@ResponseBody
	@RequestMapping("/register")
	public PageResult register(String telephone, String password, String name, String identificationNo, String countyGuid, String verifyCode) {
		if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(name) || StringUtils.isEmpty(identificationNo)
				|| StringUtils.isEmpty(countyGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = babysitterService.register(telephone, password, name, identificationNo, countyGuid, verifyCode, res);
		return result;
	}

	@ResponseBody
	@RequestMapping("/login")
	public PageResult login(String telephone, String password) {
		if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = babysitterService.login(telephone, password, res);
		return result;
	}

	@ResponseBody
	@RequestMapping("/changepass")
	public PageResult changePassword(String telephone, String code, String password) {
		if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = babysitterService.changePass(telephone, password, code, res);
		return result;
	}

	@ResponseBody
	@RequestMapping("/credentials")
	public PageResult credentials(String guid) {
		if (StringUtils.isEmpty(guid))
			return getErrRes(ResultInfo.INF_EMPTY);
		Babysitter babysitter = babysitterService.getBabysitter(guid);
		res.put("result", babysitter.view().getCredentials());
		return res;
	}

	@ResponseBody
	@RequestMapping("/addRest")
	public PageResult addRest(String guid, String beginDate, String endDate, String memo) {
		if (StringUtils.isEmpty(guid) || StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate))
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
		RestInfo info = babysitterService.addRestInfo(guid, beginDated, endDated, memo);
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
		res = babysitterService.addLowerSalary(guid, money, res);
		return res;
	}

	@ResponseBody
	@RequestMapping("/joinPromotion")
	public PageResult joinPromotion(String guid, String promotionGuid) {
		if (StringUtils.isEmpty(guid) || StringUtils.isEmpty(promotionGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		res = babysitterService.joinPromotion(guid, promotionGuid, res);
		return res;
	}

	@ResponseBody
	@RequestMapping("/notice")
	public PageResult getCompanyNotice(String guid, Pagination<CompanyNotice> page) {
		if (StringUtils.isEmpty(guid))
			return getResult(ResultInfo.INF_EMPTY);
		Pagination<CompanyNoticeView> pa = noticeService.getPaginationNotice(page, guid);
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
}
