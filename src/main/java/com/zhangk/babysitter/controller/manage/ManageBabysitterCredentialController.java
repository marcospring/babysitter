package com.zhangk.babysitter.controller.manage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.common.CredentialService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterView;
import com.zhangk.babysitter.viewmodel.CredentialView;

@Controller
@RequestMapping("/manage/credential")
public class ManageBabysitterCredentialController extends BaseController {
	@Autowired
	private BabysitterService babysitterService;
	@Autowired
	private CredentialService credentialService;

	@RequestMapping("/go")
	public String go() {
		return "/manage/babysitterCredential/babysitter";
	}

	@ResponseBody
	@RequestMapping("/babysitterList")
	public Object list(HttpServletRequest request, int page, int rows,
			String countyId, String name, String levelid, String telephone,
			String cardNo, String identificationNo) {
		Pagination<Babysitter> babysitters = new Pagination<Babysitter>(page,
				rows);
		Pagination<BabysitterView> views = babysitterService
				.getManageBabysitters(babysitters, countyId, name, levelid,
						telephone, cardNo, identificationNo);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}

	@RequestMapping("/goAdd")
	public Object goAdd() {
		return "/manage/babysitterCredential/credential";
	}

	@ResponseBody
	@RequestMapping("/goCredentialList")
	public Object goCredentialList(HttpServletRequest request, int page,
			int rows, String credentialName, String babysitterNo, String name) {

		Pagination<CredentialView> credentials = new Pagination<CredentialView>(
				page, rows);
		Pagination<CredentialView> views = credentialService
				.getManageCredentials(credentials, credentialName,
						babysitterNo, name);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}

	@ResponseBody
	@RequestMapping("/check")
	public Object check(HttpServletRequest request, String flag,
			String credentialId) {
		if (StringUtils.isEmpty(flag) || StringUtils.isEmpty(credentialId)) {
			return MyResponse.errorResponse(ResultInfo.INF_EMPTY.getCode(),
					ResultInfo.INF_EMPTY.getMsg());
		}
		ResultInfo result = ResultInfo.BAD_REQUEST;
		try {
			int flagInt = Integer.valueOf(flag);
			long credentialIdLong = Long.valueOf(credentialId);
			result = credentialService.manageCheckCredential(credentialIdLong,
					flagInt);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return MyResponse.errorResponse(ResultInfo.BAD_REQUEST.getCode(),
					ResultInfo.BAD_REQUEST.getMsg());
		}

		return result == ResultInfo.SUCCESS ? MyResponse.successResponse()
				: MyResponse.errorResponse(ResultInfo.BAD_REQUEST.getCode(),
						ResultInfo.BAD_REQUEST.getMsg());
	}
}
