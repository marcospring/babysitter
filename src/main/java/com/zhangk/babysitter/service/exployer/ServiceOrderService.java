package com.zhangk.babysitter.service.exployer;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.entity.CustomerManager;
import com.zhangk.babysitter.entity.ServiceOrder;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterView;
import com.zhangk.babysitter.viewmodel.ServiceOrderView;

public interface ServiceOrderService {
	/**
	 * 月嫂添加私单
	 *
	 * @param date
	 *            预产期
	 * @param price
	 *            价格
	 * @param countyGuid
	 *            城市GUID
	 * @param address
	 *            地址
	 * @param name
	 *            雇主姓名
	 * @param mobile
	 *            雇主电话
	 * @param checkCode
	 *            验证码
	 * @return
	 */
	ResultInfo addServiceOrder(String date, String price, String countyGuid,
			String address, String name, String mobile, String checkCode);

	PageResult wechatAddServiceOrder(String date, String price,
			String countyGuid, String address, String name, String mobile,
			String checkCode, String openid, PageResult result,
			String isNotAdvice);

	ResultInfo addBabysitterOrderEvaluate(String orderGuid, String msg,
			String score);

	PageResult orderList(String mobile, String openid, PageResult result);

	Pagination<ServiceOrderView> manageOrderList(Pagination<ServiceOrder> page,
			String exployerName, String telephone);

	ResultInfo manageAddOrder(String beginDate, String endDate, String price,
			String address, String employerName, String telephone);

	void deleteOrder(String ids);

	ServiceOrder getOrder(String id);

	ResultInfo manageEditOrder(String id, String employerAddress, String price,
			String beginDate, String endDate);

	/**
	 * 雇主选择抢单月嫂
	 *
	 * @param babysitterGuid
	 * @param orderGuid
	 * @param result
	 * @return
	 */
	PageResult markBabysitter(String babysitterGuid, String orderGuid,
			PageResult result);

	void addBabysitterAdvice(String countyGuid, ServiceOrder order,
			Map<String, Date> expectedDate, String countyLevelGuid);

	void addServiceOrderAdvice(String id);

	void addServiceOrderPanic(String babysitterId, String serviceOrderId);

	List<BabysitterView> getPanicBabysitters(String serviceOrderId);

	PageResult markBabysitterId(String babysitterId, String serviceOrderId,
			PageResult result);

	CustomerManager getCustomerManager(String countyGuid, String week);

	PageResult endService(String orderGuid);

	PageResult payFrontMoney(String orderNo, String ip, PageResult result,
			String openid);

	ServiceOrder addEmployerServiceOrder(String date, String price,
			String countyGuid, String address, String name, String mobile,
			String checkCode, String openid);

	PageResult addMakeBabysitterOrder(String date, String address, String name,
			String mobile, String checkCode, String openid, String countyGuid,
			String babysitterGuid, PageResult result);

	PageResult getBabysitterOrderInfo(String serviceOrderGuid, PageResult result);

	PageResult serviceEnd(String orderGuid, PageResult result);

	PageResult orderHistoryRecord(String orderGuid, PageResult result);
}
