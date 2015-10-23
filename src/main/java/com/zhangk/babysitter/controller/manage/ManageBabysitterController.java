package com.zhangk.babysitter.controller.manage;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterView;

@Controller
@RequestMapping("/manage/babysitter")
public class ManageBabysitterController extends BaseController {

	@Autowired
	private BabysitterService babysitterService;

	@RequestMapping("/go")
	public String go() {
		return "/manage/babysitter/babysitter";
	}

	@RequestMapping("/goManage")
	public String goManage() {
		return "/manage/babysitter/manageBabysitter";
	}

	@ResponseBody
	@RequestMapping("/list")
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
	public Object goAdd(HttpServletRequest request) {

		return "manage/babysitter/babysitterAdd";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(String name, String password, String identificationNo,
			long lowerSalary, String mobilePhone, long countyId, long levelId,
			String nativePlace, String bankName, String bankCardNo,
			String bankUserName, String introduce) {
		// 定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
		Pattern idNumPattern = Pattern
				.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		// 通过Pattern获得Matcher
		Matcher idNumMatcher = idNumPattern.matcher(identificationNo);
		StringBuffer birthday = new StringBuffer();
		// 判断用户输入是否为身份证号
		if (idNumMatcher.matches()) {
			// 如果是，定义正则表达式提取出身份证中的出生日期
			Pattern birthDatePattern = Pattern
					.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");// 身份证上的前6位以及出生年月日
			// 通过Pattern获得Matcher
			Matcher birthDateMather = birthDatePattern
					.matcher(identificationNo);
			// 通过Matcher获得用户的出生年月日
			if (birthDateMather.find()) {
				String year = birthDateMather.group(1);
				String month = birthDateMather.group(2);
				String date = birthDateMather.group(3);
				birthday.append(year).append("-").append(month).append("-")
						.append(date);
			}
		} else {
			return MyResponse.errorResponse(
					ResultInfo.IDENTIFICATION_NO_INVALID.getCode(),
					ResultInfo.IDENTIFICATION_NO_INVALID.getMsg());
		}
		ResultInfo result = babysitterService.manageAddBabysitter(name,
				password, identificationNo, lowerSalary, mobilePhone, countyId,
				levelId, birthday.toString(), nativePlace, bankName,
				bankCardNo, bankUserName, introduce);
		if (result == ResultInfo.BABYSITTER_NOT_NULL)
			return MyResponse.errorResponse(
					ResultInfo.BABYSITTER_NOT_NULL.getCode(),
					ResultInfo.BABYSITTER_NOT_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, long id) {
		Babysitter babysitter = babysitterService.getBabysitter(id);
		request.setAttribute("vo", babysitter);
		return "manage/babysitter/babysitterEdit";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object update(String id, String name, String password,
			String identificationNo, long lowerSalary, String mobilePhone,
			long countyId, long levelId, String birthday, String nativePlace,
			String introduce, String height, String weight, String hobbies,
			String bankName, String bankCardNo, String bankUserName,
			String mandarin, String isV) {
		ResultInfo result = babysitterService.manageUpdateBabysitter(id, name,
				password, identificationNo, lowerSalary, mobilePhone, countyId,
				levelId, birthday, nativePlace, introduce, height, weight,
				hobbies, bankName, bankCardNo, bankUserName, mandarin, isV);
		if (result == ResultInfo.BABYSITTER_NULL)
			return MyResponse.errorResponse(
					ResultInfo.BABYSITTER_NULL.getCode(),
					ResultInfo.BABYSITTER_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(String id) {
		babysitterService.manageDeleteBabysitter(id);
		return MyResponse.successResponse();
	}

	@RequestMapping("/goVerify")
	public Object goVerify(HttpServletRequest request) {
		return "manage/babysitter/verifyBabysitter";
	}

	@ResponseBody
	@RequestMapping("/verify")
	public Object verify(String ids, String state) {
		babysitterService.verify(ids, state);
		return MyResponse.successResponse();
	}
}
