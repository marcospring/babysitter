package com.zhangk.babysitter.service.babysitter;

import com.zhangk.babysitter.controller.BaseController.PageResult;
import com.zhangk.babysitter.entity.BabysitterOrder;
import com.zhangk.babysitter.utils.common.Pagination;
import com.zhangk.babysitter.utils.common.ResultInfo;
import com.zhangk.babysitter.viewmodel.BabysitterOrderView;

public interface BabysitterOrderService {

	String FRONT = "front";
	String END = "end";

	ResultInfo addBabysitterOrder(String babysitterGuid, String countyGuid,
			String address, String name, String mobile, String date,
			String price);

	int updateBabysitterOrder(String orderNO, String transactionId, int state);

	Pagination<BabysitterOrderView> manageOrderList(
			Pagination<BabysitterOrder> page, String babysitterGuid,
			String babysitterName, String employerName,
			String employerTelephone, String state);

	ResultInfo manageAddOrder(String guid, String beginDate, String endDate,
			String price, String address, String employerName, String telephone);

	void deleteOrder(String ids);

	BabysitterOrder getOrder(String id);

	ResultInfo manageEditOrder(String id, String employerName,
			String employerTelephone, String employerAddress, String price,
			String beginDate, String endDate, int state);

	PageResult getOrderInfo(String orderNo, PageResult result);

}
