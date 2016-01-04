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
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.service.babysitter.BabysitterOrderService;
import com.zhangk.babysitter.service.babysitter.BabysitterService;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterOrderView;
import com.zhangk.babysitter.viewmodel.BabysitterView;

@Controller
@RequestMapping("/manage/babysitterOrder")
public class ManageBabysitterOrderController extends BaseController {
	@Autowired
	private BabysitterOrderService orderService;
	@Autowired
	private BabysitterService babysitterService;

	@RequestMapping("/go")
	public String go() {
		return "/manage/babysitterOrder/order";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, int page, int rows,
			String babysitterGuid, String babysitterName, String employerName,
			String employerTelephone, String state) {
		Pagination<BabysitterOrder> order = new Pagination<BabysitterOrder>(
				page, rows);
		Pagination<BabysitterOrderView> views = orderService.manageOrderList(
				order, babysitterGuid, babysitterName, employerName,
				employerTelephone, state);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", views.getResult());
		result.put("total", views.getResultSize());
		return result;
	}

	@RequestMapping("/goAdd")
	public Object goAdd(HttpServletRequest request, String id) {
		Babysitter babysitter = babysitterService.getBabysitter(id);
		request.setAttribute("vo", babysitter);
		return "manage/babysitterOrder/addOrder";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Object add(String guid, String beginDate, String endDate,
			String price, String address, String employerName, String telephone) {
		ResultInfo result = orderService.manageAddOrder(guid, beginDate,
				endDate, price, address, employerName, telephone);
		if (result == ResultInfo.BABYSITTER_NULL)
			return MyResponse.errorResponse(
					ResultInfo.BABYSITTER_NULL.getCode(),
					ResultInfo.BABYSITTER_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/goManage")
	public Object goManage(HttpServletRequest request) {

		return "manage/babysitterOrder/manageOrder";
	}

	@RequestMapping("/goDelete")
	public Object goDelete(String ids) {
		return "manage/babysitterOrder/deleteOrder";
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(String ids) {
		orderService.deleteOrder(ids);
		return MyResponse.successResponse();
	}

	@RequestMapping("/goEdit")
	public Object goEdit(HttpServletRequest request, String id) {
		BabysitterOrder order = orderService.getOrder(id);
		request.setAttribute("vo", order);
		return "manage/babysitterOrder/editOrder";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Object edit(String id, String employerName,
			String employerTelephone, String employerAddress, String price,
			String beginDate, String endDate, int state) {
		ResultInfo result = orderService.manageEditOrder(id, employerName,
				employerTelephone, employerAddress, price, beginDate, endDate,
				state);
		if (result == ResultInfo.COMPANY_NULL)
			return MyResponse.errorResponse(ResultInfo.COMPANY_NULL.getCode(),
					ResultInfo.COMPANY_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/goAddOrder")
	public String goAddOrder() {
		return "/manage/babysitterOrder/babysitter";
	}

	@ResponseBody
	@RequestMapping("/babysitterList")
	public Object babysitterList(HttpServletRequest request, int page,
			int rows, String countyId, String name, String levelid,
			String telephone, String cardNo, String identificationNo) {
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

	@RequestMapping("/orderUpdate")
	public Object orderUpdate(HttpServletRequest request) {

		return "manage/babysitterOrder/orderUpdate";
	}

	@RequestMapping("/collectMoney")
	public Object collectMoney(HttpServletRequest request) {

		return "manage/babysitterOrder/collectMoney";
	}

	@ResponseBody
	@RequestMapping("/submitCollectMoney")
	public Object submitCollectMoney(String id, String orderFrontPrice) {
		ResultInfo info = orderService.payFrountMoney(id, orderFrontPrice);
		if (info != ResultInfo.SUCCESS)
			return MyResponse.errorResponse(
					ResultInfo.BABYSITTER_ORDER_NULL.getCode(),
					ResultInfo.BABYSITTER_ORDER_NULL.getMsg());
		return MyResponse.successResponse();
	}

	@ResponseBody
	@RequestMapping("/submitEndMoney")
	public Object submitEndMoney(String id) {
		ResultInfo info = orderService.payEndMoney(id);
		if (info != ResultInfo.SUCCESS)
			return MyResponse.errorResponse(
					ResultInfo.BABYSITTER_ORDER_NULL.getCode(),
					ResultInfo.BABYSITTER_ORDER_NULL.getMsg());
		return MyResponse.successResponse();
	}
}
