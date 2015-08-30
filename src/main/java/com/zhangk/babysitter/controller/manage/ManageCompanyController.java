package com.zhangk.babysitter.controller.manage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Company;
import com.zhangk.babysitter.service.company.CompanyService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.CompanyView;

@Controller
@RequestMapping("/manage/company")
public class ManageCompanyController extends BaseController {
	@Autowired
	private CompanyService companyService;

	@RequestMapping("/go")
	public String go() {
		return "/manage/company/company";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, int page, int rows,
			String countyId, String companyName, String type, String state) {
		Pagination<Company> companys = new Pagination<Company>(page, rows);
		Pagination<CompanyView> views = companyService.manageCompanyList(
				companys, companyName, countyId, type, state);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}

	@RequestMapping("/goAdd")
	public Object goAdd(HttpServletRequest request) {

		return "manage/company/addCompany";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(String name, String telephone, int countyid, int type,
			String address, String countyLevel) {
		ResultInfo result = companyService.manageAddCompany(name, telephone,
				countyid, type, address, countyLevel);
		if (result == ResultInfo.COMPANY_NOT_NULL)
			return MyResponse.errorResponse(
					ResultInfo.COMPANY_NOT_NULL.getCode(),
					ResultInfo.COMPANY_NOT_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/goManage")
	public Object goManage(HttpServletRequest request) {

		return "manage/company/manageCompany";
	}

	@RequestMapping("/goVerify")
	public Object goVerify(HttpServletRequest request) {

		return "manage/company/verifyCompany";
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(String ids) {
		companyService.deleteCompany(ids);
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/verify")
	public Object verify(String ids, String state) {
		companyService.verify(ids, state);
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, String id) {
		Company company = companyService.getCompany(id);
		request.setAttribute("vo", company);
		return "manage/company/editCompany";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object edit(String id, String name, String telephone, int countyid,
			int type, String address, String countyLevel) {
		ResultInfo result = companyService.manageEditCompany(id, name,
				telephone, countyid, type, address, countyLevel);
		if (result == ResultInfo.COMPANY_NULL)
			return MyResponse.errorResponse(ResultInfo.COMPANY_NULL.getCode(),
					ResultInfo.COMPANY_NULL.getMsg());
		return MyResponse.successResponse();
	}
}
