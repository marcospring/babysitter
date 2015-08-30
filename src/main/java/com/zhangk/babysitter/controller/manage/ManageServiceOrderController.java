package com.zhangk.babysitter.controller.manage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangk.babysitter.controller.BaseController;
import com.zhangk.babysitter.entity.Employer;
import com.zhangk.babysitter.entity.ServiceOrder;
import com.zhangk.babysitter.service.exployer.EmployerService;
import com.zhangk.babysitter.service.exployer.ServiceOrderService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.EmployerView;
import com.zhangk.babysitter.viewmodel.ServiceOrderView;

@Controller
@RequestMapping("/manage/serviceOrder")
public class ManageServiceOrderController extends BaseController {
	@Autowired
	private ServiceOrderService orderService;
	@Autowired
	private EmployerService employerService;

	@RequestMapping("/go")
	public String go() {
		return "/manage/serviceOrder/order";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, int page, int rows,
			String exployerName, String telephone) {
		Pagination<ServiceOrder> order = new Pagination<ServiceOrder>(page,
				rows);
		Pagination<ServiceOrderView> views = orderService.manageOrderList(
				order, exployerName, telephone);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}

	@RequestMapping("/goAdd")
	public Object goAdd(HttpServletRequest request, String id) {
		Employer employer = employerService.getEmployer(id);
		request.setAttribute("vo", employer);
		return "manage/serviceOrder/addOrder";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(String guid, String beginDate, String endDate,
			String price, String address, String employerName, String telephone) {
		orderService.manageAddOrder(beginDate, endDate, price, address,
				employerName, telephone);
		return MyResponse.successResponse();
	}

	@RequestMapping("/goManage")
	public Object goManage(HttpServletRequest request) {

		return "manage/serviceOrder/manageOrder";
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(String ids) {
		orderService.deleteOrder(ids);
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, String id) {
		ServiceOrder order = orderService.getOrder(id);
		request.setAttribute("vo", order);
		return "manage/serviceOrder/editOrder";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object edit(String id, String employerAddress, String price,
			String beginDate, String endDate) {
		ResultInfo result = orderService.manageEditOrder(id, employerAddress,
				price, beginDate, endDate);
		if (result == ResultInfo.SERVICE_ORDER_NULL)
			return MyResponse.errorResponse(
					ResultInfo.SERVICE_ORDER_NULL.getCode(),
					ResultInfo.SERVICE_ORDER_NULL.getMsg());
		if (result == ResultInfo.EMPLOYER_NULL)
			return MyResponse.errorResponse(ResultInfo.EMPLOYER_NULL.getCode(),
					ResultInfo.EMPLOYER_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/goAddOrder")
	public String goAddOrder() {
		return "/manage/serviceOrder/employer";
	}

	@ResponseBody
	@RequestMapping("/employerList")
	public Object babysitterList(HttpServletRequest request, int page,
			int rows, String name, String telephone) {
		Pagination<Employer> employer = new Pagination<Employer>(page, rows);
		Pagination<EmployerView> views = employerService
				.getPageEmployerListForOrder(employer, name, telephone);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}
}
