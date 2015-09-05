package com.zhangk.babysitter.service.exployer;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.entity.ServiceOrder;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.ServiceOrderView;

public interface ServiceOrderService {
	ResultInfo addServiceOrder(String date, String price, String countyGuid,
			String address, String name, String mobile, String checkCode);

	PageResult wechatAddServiceOrder(String date, String price,
			String countyGuid, String address, String name, String mobile,
			String checkCode, String openid, PageResult result);

	ResultInfo addBabysitterOrderEvaluate(String employGuid, String orderGuid,
			String babysitterGuid, String msg, String score);

	PageResult orderList(String mobile, String openid, PageResult result);

	Pagination<ServiceOrderView> manageOrderList(Pagination<ServiceOrder> page,
			String exployerName, String telephone);

	ResultInfo manageAddOrder(String beginDate, String endDate, String price,
			String address, String employerName, String telephone);

	void deleteOrder(String ids);

	ServiceOrder getOrder(String id);

	ResultInfo manageEditOrder(String id, String employerAddress, String price,
			String beginDate, String endDate);
}
