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

	public static final int EDIT_TYPE = 1;
	public static final int NEW_EDIT_TYPE = 2;
	public static final int BABYSITTER_IN_ORDER = 3;
	public static final int BABYSITTER_END_ORDER = 4;

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
	public Object goEdit(HttpServletRequest request, String id, int type) {
		BabysitterOrder order = orderService.getOrder(id);
		request.setAttribute("vo", order);
		if (type == EDIT_TYPE) {
			return "manage/babysitterOrder/editOrder";
		} else if (type == NEW_EDIT_TYPE) {
			return "manage/babysitterOrder/newEditOrder";
		} else if (type == BABYSITTER_IN_ORDER) {
			return "manage/babysitterOrder/babysitterFamily";
		} else if (type == BABYSITTER_END_ORDER) {
			return "manage/babysitterOrder/babysitterPauper";
		} else {
			return "manage/babysitterOrder/paymentEditOrder";
		}
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
			return MyResponse.errorResponse(info.getCode(), info.getMsg());
		return MyResponse.successResponse();
	}

	/**
	 * 转至收尾款页面
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/payment")
	public Object payment(HttpServletRequest request) {

		return "manage/babysitterOrder/payment";
	}

	@ResponseBody
	@RequestMapping("/submitEndMoney")
	public Object submitEndMoney(String id) {
		ResultInfo info = orderService.payEndMoney(id);
		if (info != ResultInfo.SUCCESS)
			return MyResponse.errorResponse(info.getCode(), info.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/adviceInOrder")
	public Object adviceInOrder(String id, String beginDate) {
		ResultInfo info = orderService.adviceInOrder(id, beginDate);
		if (info != ResultInfo.SUCCESS)
			return MyResponse.errorResponse(info.getCode(), info.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/family")
	public Object family(HttpServletRequest request) {

		return "manage/babysitterOrder/family";
	}

	@ResponseBody
	@RequestMapping("/submitFamily")
	public Object submitFamily(String id) {
		ResultInfo info = orderService.submitFamily(id);
		if (info != ResultInfo.SUCCESS)
			return MyResponse.errorResponse(info.getCode(), info.getMsg());
		return MyResponse.successResponse();
	}

	@RequestMapping("/pauper")
	public Object pauper(HttpServletRequest request) {

		return "manage/babysitterOrder/pauper";
	}

	@ResponseBody
	@RequestMapping("/submitPauper")
	public Object submitPauper(String id) {
		ResultInfo info = orderService.submitPauper(id);
		if (info != ResultInfo.SUCCESS)
			return MyResponse.errorResponse(info.getCode(), info.getMsg());
		return MyResponse.successResponse();
	}
}
