package com.zhangk.babysitter.controller.manage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Babysitter;
import com.zhangk.babysitter.entity.CompanyNotice;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.service.common.NoticeService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.viewmodel.BabysitterView;
import com.zhangk.babysitter.viewmodel.CompanyNoticeView;

@Controller
@RequestMapping("/manage/companyNotice")
public class ManageCompanyNoticeController extends BaseController {

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private BabysitterService babysitterService;

	@RequestMapping("/go")
	public String go() {
		return "/manage/companyNotice/babysitterList";
	}

	@RequestMapping("/goNotice")
	public String goNotice() {
		return "/manage/companyNotice/noticeList";
	}

	@RequestMapping("/goSend")
	public String goSend() {
		return "/manage/companyNotice/sendNotice";
	}

	@RequestMapping("/send")
	public Object send(String ids, String title, String memo) {
		noticeService.addNotices(title, memo, ids);
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, int page, int rows, String countyId, String name, String levelid, String telephone, String cardNo) {
		Pagination<Babysitter> babysitters = new Pagination<Babysitter>(page, rows);
		Pagination<BabysitterView> views = babysitterService.getManageBabysitters(babysitters, countyId, name, levelid, telephone, cardNo);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}

	@ResponseBody
	@RequestMapping("/noticeList")
	public Object noticeList(HttpServletRequest request, int page, int rows, String name) {
		Pagination<CompanyNotice> notices = new Pagination<CompanyNotice>(page, rows);
		Pagination<CompanyNoticeView> views = noticeService.getManageNotices(notices, name);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}
}
