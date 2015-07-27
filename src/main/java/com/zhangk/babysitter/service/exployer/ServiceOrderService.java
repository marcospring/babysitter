package com.zhangk.babysitter.service.exployer;

import com.zhangk.babysitter.utils.common.ResultInfo;

public interface ServiceOrderService {
	ResultInfo addServiceOrder(String date, String price, String countyGuid,
			String address, String name, String mobile, String checkCode);

	ResultInfo addBabysitterOrderEvaluate(String employGuid, String orderGuid,
			String babysitterGuid, String msg, String score);
}
