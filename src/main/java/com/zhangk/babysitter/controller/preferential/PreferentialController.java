package com.zhangk.babysitter.controller.preferential;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.service.preferential.PreferentialService;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.PreferentialVO;

@Controller
@RequestMapping("/preferential")
public class PreferentialController extends BaseController {

	@Autowired
	private PreferentialService service;

	@ResponseBody
	@RequestMapping("/preferentislTypes")
	public PageResult preferentislTypes() {
		PageResult result = service.preferentialTypes(getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/addPromoter")
	public PageResult addPreferentialPromoter(HttpServletRequest request,
			String telephone, String promoterTypeGuid) {
		if (StringUtils.isEmpty(telephone)
				|| StringUtils.isEmpty(promoterTypeGuid))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = service.addPromoter(request, telephone,
				promoterTypeGuid, getResult());
		return result;

	}

	@RequestMapping("/{flag}/promoterUrl")
	public String promoterUrl(@PathVariable String flag) {
		if (StringUtils.isEmpty(flag))
			return "error";
		PreferentialVO vo = service.getPreferential(flag);
		request.setAttribute("preferential", vo);
		return "/preferential/preferentialInfo";
	}

	@RequestMapping("/addPreferentialReceiver")
	public String addPreferentialReceiver(String preferentialValue,
			String toUserTelephone, String verifyCode) {
		if (StringUtils.isEmpty(preferentialValue)
				|| StringUtils.isEmpty(toUserTelephone)
				|| StringUtils.isEmpty(verifyCode))
			request.setAttribute("preferential",
					getErrRes(ResultInfo.INF_EMPTY));
		PageResult result = service.addPreferentialReceiver(preferentialValue,
				toUserTelephone, verifyCode, getResult());
		request.setAttribute("preferential", result);
		return "/preferential/preferentialResult";
	}

	@ResponseBody
	@RequestMapping("/searchPreferential")
	public PageResult preferentialList(String phone) {
		if (StringUtils.isEmpty(phone))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = service.preferentialList(phone, getResult());
		return result;
	}

	@ResponseBody
	@RequestMapping("/preferentialPromoterList")
	public PageResult preferentialPromoterList(String phone) {
		if (StringUtils.isEmpty(phone))
			return getErrRes(ResultInfo.INF_EMPTY);
		PageResult result = service
				.preferentialPromoterList(phone, getResult());
		return result;
	}

}
