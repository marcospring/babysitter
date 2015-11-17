package com.zhangk.babysitter.controller.mobile.babysitter;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.CompanyNotice;
import com.zhangk.babysitter.entity.RestInfo;
import com.zhangk.babysitter.service.babysitter.BabysitterOrderService;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.common.NoticeService;
import com.zhangk.babysitter.service.common.PromotionService;
import com.zhangk.babysitter.utils.common.ExpectedDateCreate;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterView;
import com.zhangk.babysitter.viewmodel.CompanyNoticeView;
import com.zhangk.babysitter.viewmodel.PromotionView;

@Controller("mobileBabysitterController")
@RequestMapping("/mobile/babysitter")
public class BabysitterController extends BaseController {

	@Autowired
	private BabysitterService babysitterService;
	@Autowired
	private PromotionService proService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private BabysitterOrderService orderService;

	@ResponseBody
	@RequestMapping("/babysitterList")
	public PageResult areaBabysitters(String countyGuid,
			Pagination<BabysitterView> page, String name, String orderStr) {
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

	// @ResponseBody
	// @RequestMapping("/recommend")
	// public PageResult getRecommond(String countyGuid) {
	// if (StringUtils.isEmpty(countyGuid))
	// return getErrRes(ResultInfo.INF_EMPTY);
	// RecommendInfo info =
	// babysitterService.getNewBabysitterRecommend(countyGuid);
	// if (info == null)
	// return getErrRes(ResultInfo.RECOMMEND_NULL);
	// RecommendInfoView view = info.view();
	// view.setBabysitterCount(babysitterService.getBabysitterCountByCounty(countyGuid));
	// PageResult pres = getResult(ResultInfo.SUCCESS);
	// pres.put("result", view);
	//
	// return pres;
	// }

	@ResponseBody
	@RequestMapping("/register")
	public PageResult register(String telephone, String password, String name,
			String identificationNo, String countyGuid, String verifyCode) {
		if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(name)
				|| StringUtils.isEmpty(identificationNo)
				|| StringUtils.isEmpty(countyGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		// Pattern reg15 = Pattern
		// .compile("^[1-9]\\d{7}((0\\[1-9])|(1[0-2]))(([0\\[1-9]|1\\d|2\\d])|3[0-1])\\d{2}([0-9]|x|X){1}$");
		// Pattern reg18 = Pattern
		// .compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\[1-9]))|((1[0-2]))(([0\\[1-9]|1\\d|2\\d])|3[0-1])\\d{3}([0-9]|x|X){1}$");
		PageResult result = babysitterService.register(telephone, password,
				name, identificationNo, countyGuid, verifyCode, getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/checkRegister")
	public PageResult registerCheckMobile(String telephone) {
		if (StringUtils.isEmpty(telephone)) {
			return getErrRes(ResultInfo.INF_EMPTY);
		}
		return babysitterService.checkBabysitterMobile(telephone, getResult());
	}

	@ResponseBody
	@RequestMapping("/login")
	public PageResult login(String telephone, String password) {
		if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = babysitterService.login(telephone, password,
				getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/changepass")
	public PageResult changePassword(String telephone, String code,
			String password) {
		if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(code))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = babysitterService.changePass(telephone, password,
				code, getResult());
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
	public PageResult addRest(String guid, String beginDate, String endDate,
			String memo) {
		if (StringUtils.isEmpty(guid) || StringUtils.isEmpty(beginDate)
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
		RestInfo info = babysitterService.addRestInfo(guid, beginDated,
				endDated, memo);
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
	public PageResult getCompanyNotice(String guid,
			Pagination<CompanyNotice> page) {
		if (StringUtils.isEmpty(guid))
			return getResult(ResultInfo.INF_EMPTY);
		Pagination<CompanyNoticeView> pa = noticeService.getPaginationNotice(
				page, guid);
		PageResult result = getResult();
		result.setResult(ResultInfo.SUCCESS);
		result.put("result", pa);
		return result;
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
	public PageResult updateBankCard(String guid, String bankName,
			String bankCardNo, String bankUserName, String cardGuid) {
		if (StringUtils.isEmpty(bankName) || StringUtils.isEmpty(bankCardNo)
				|| StringUtils.isEmpty(bankUserName)) {
			return getErrRes(ResultInfo.INF_EMPTY);
		}
		res = babysitterService.updateBankCard(guid, bankName, bankCardNo,
				bankUserName, cardGuid, getResult());
		return res;
	}

	@ResponseBody
	@RequestMapping("/addOrder")
	public PageResult addOrder(String guid, String beginDate, String endDate,
			String price, String address, String employerName, String telephone) {
		if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)
				|| StringUtils.isEmpty(price) || StringUtils.isEmpty(address)
				|| StringUtils.isEmpty(employerName)
				|| StringUtils.isEmpty(telephone))
			return getErrRes(ResultInfo.INF_EMPTY);

		return babysitterService.addOrder(guid, beginDate, endDate, price,
				address, employerName, telephone, res);
	}

	@ResponseBody
	@RequestMapping("/orderSort")
	public PageResult orderSort(String countyGuid) {
		return babysitterService.orderScore(getResult(), countyGuid);
	}

	@ResponseBody
	@RequestMapping("/scoreSort")
	public PageResult scoreSort(String countyGuid) {
		return babysitterService.scoreSort(getResult(), countyGuid);
	}

	@ResponseBody
	@RequestMapping("/addCredential")
	public PageResult addCredential(HttpServletRequest request,
			String babysitterGuid, String credentialGuid) {
		PageResult result = babysitterService.addCredential(request,
				babysitterGuid, credentialGuid, getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/getServiceOrder")
	public PageResult adviceBabysitter(String babysitterGuid) {
		if (StringUtils.isEmpty(babysitterGuid)) {
			return getErrRes(ResultInfo.INF_EMPTY);
		}
		PageResult result = babysitterService.getAdvice(babysitterGuid,
				getResult());

		return result;
	}

	@ResponseBody
	@RequestMapping("/panic")
	public PageResult panicBuyingOrder(String babysitterGuid, String orderGuid) {
		if (StringUtils.isEmpty(babysitterGuid)
				|| StringUtils.isEmpty(orderGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = babysitterService.panic(babysitterGuid, orderGuid,
				getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/panicOrders")
	public PageResult panicOrders(int pageNo, int pageSize,
			String babysitterGuid) {
		if (StringUtils.isEmpty(babysitterGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		if (StringUtils.isEmpty(pageNo))
			pageNo = 0;
		if (StringUtils.isEmpty(pageSize))
			pageSize = 10;
		PageResult result = babysitterService.panicOrders(pageNo, pageSize,
				babysitterGuid, getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/orderInfo")
	public PageResult orderInfo(String orderNo) {
		if (StringUtils.isEmpty(orderNo))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = orderService.getOrderInfo(orderNo, getResult());
		return result;
	}
}
